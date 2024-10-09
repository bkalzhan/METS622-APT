package edu.bu.metcs622;

import java.io.*;

public class DownloadPartialXML {

    public static void main(String[] args) {
        String inputFile = "/Users/bekzatkalzan/Desktop/BU/METCS622_APT/Homework3/dblp.xml";
        String outputFile = "/Users/bekzatkalzan/Desktop/BU/METCS622_APT/Homework3/small_part.xml";
        String lastEntriesFile = "/Users/bekzatkalzan/Desktop/BU/METCS622_APT/Homework3/last_250000_entries.xml";
        int numLinesToExtract = 120000;  // Adjust this based on how many lines you want
        int lastEntriesCount = 100;       // Number of last entries to keep
        
        try {
            // Create a BufferedReader to read the XML file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            BufferedWriter lastEntriesWriter = new BufferedWriter(new FileWriter(lastEntriesFile));

            // To store the last 100 lines
            String[] lastEntries = new String[lastEntriesCount];
            int lineCount = 0; // Total line count for writing first N lines
            String line;

            // Read through the XML file line by line
            while ((line = reader.readLine()) != null) {
                // Write the first numLinesToExtract lines to the output file
                if (lineCount < numLinesToExtract) {
                    writer.write(line);
                    writer.newLine();
                }

                // Store the last 100 entries in the array
                if (lineCount < lastEntriesCount) {
                    lastEntries[lineCount] = line;  // Fill up the array
                } else {
                    // Shift the array to keep only the last 100 lines
                    System.arraycopy(lastEntries, 1, lastEntries, 0, lastEntriesCount - 1);
                    lastEntries[lastEntriesCount - 1] = line; // Add the new line at the end
                }

                lineCount++;
            }

            // Close the readers and writers
            writer.close();
            reader.close();

            // Write the last 100 lines to the lastEntries file
            for (String lastLine : lastEntries) {
                if (lastLine != null) {
                    lastEntriesWriter.write(lastLine);
                    lastEntriesWriter.newLine();
                }
            }
            lastEntriesWriter.close();

            System.out.println("Successfully extracted " + numLinesToExtract + " lines into " + outputFile);
            System.out.println("Successfully extracted the last " + lastEntriesCount + " entries into " + lastEntriesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
