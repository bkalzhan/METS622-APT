package edu.bu.metcs622;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SearchMemory {
    private static Map<String, LocalDateTime> searchMemory = new HashMap<>();
    private static Map<String, Integer> searchFrequency = new HashMap<>();

    // Add a search criterion and timestamp to the memory
    public static void addSearch(String keyword) {
        searchMemory.put(keyword, LocalDateTime.now());
        searchFrequency.put(keyword, searchFrequency.getOrDefault(keyword, 0) + 1);
    }

    // Print the search memory
    public static void printSearchMemory() {
        System.out.println("Search Memory:");
        for (Map.Entry<String, LocalDateTime> entry : searchMemory.entrySet()) {
            System.out.println("Keyword: " + entry.getKey() + ", Timestamp: " + entry.getValue());
        }
    }
    
    // Print the frequency of each search term
    public static void printSearchFrequency() {
        System.out.println("Search Frequency:");
        for (Map.Entry<String, Integer> entry : searchFrequency.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue() + " times");
        }
    }
}
