#!/bin/bash

# find_media_files.sh

# Get today's date
TODAY=$(date +%Y-%m-%d)

echo "======================================"
echo "Searching for media files modified today ($TODAY)"
echo "Looking in parent directory and its subdirectories"
echo "======================================"

# Create temporary file for storing metadata hashes
TEMP_FILE="/tmp/metadata_hashes.txt"
rm -f "$TEMP_FILE"

# Function to get metadata hash based on file type
get_metadata_hash() {
    local file="$1"
    
    # For images: get dimensions, color profile, pixel depth
    if [[ $file =~ \.(jpg|jpeg|png|gif)$ ]]; then
        sips -g pixelWidth -g pixelHeight -g format -g space -g hasAlpha 2>&1 | md5
    
    # For videos: get duration, dimensions, codec info
    elif [[ $file =~ \.(mp4|avi|mov|mkv)$ ]]; then
        mdls -name kMDItemDurationSeconds \
             -name kMDItemPixelHeight \
             -name kMDItemPixelWidth \
             -name kMDItemCodecs \
             -name kMDItemVideoBitRate 2>&1 | md5
    fi
}

echo "Scanning files and comparing metadata..."
echo "======================================"

# Find all media files and store their metadata hashes
find .././ -type f -newermt "$TODAY" ! -newermt "$TODAY + 1 day" \
    \( -iname "*.jpg" -o -iname "*.jpeg" -o -iname "*.png" -o -iname "*.gif" \
       -o -iname "*.mp4" -o -iname "*.avi" -o -iname "*.mov" -o -iname "*.mkv" \) \
    | while read -r file; do
        # echo "Found" $file
        hash=$(get_metadata_hash "$file")
        if [ ! -z "$hash" ]; then
            echo "${hash} ${file}" >> "$TEMP_FILE"
        fi
    done

if [ -f "$TEMP_FILE" ] && [ -s "$TEMP_FILE" ]; then
    echo -e "\nResults (files with identical metadata):"
    echo "======================================"
    
    # Sort by hash and find duplicates
    sort "$TEMP_FILE" | awk '
    {
        hash = $1
        file = substr($0, length($1) + 2)
        
        if (hash == prev_hash) {
            if (!printed_header) {
                print "\nPotential duplicates found:"
                print last_file
                printed_header = 1
            }
            print file
        } else {
            printed_header = 0
            last_file = file
        }
        prev_hash = hash
    }'
else
    echo "No media files found for today's date."
fi

echo -e "\n======================================"

# Cleanup
rm -f "$TEMP_FILE"