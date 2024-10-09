package edu.bu.metcs622;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // Array of file names to be merged
        String[] fileNames = new String[3];
        fileNames[0] = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/Kickstarter_2024-07-11T03_20_11_021Z.json";
        fileNames[1] = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/Kickstarter_2024-08-12T12_06_51_507Z.json";
        fileNames[2] = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/Kickstarter_2024-09-12T03_20_25_588Z.json";
        
        String outputFilePath = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/joined.json";
        
        String newOutputFilePath1 = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/searchResults1.json";
        String newOutputFilePath2 = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/searchResults2.json";
        String newOutputFilePath3 = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/searchResults3.json";
        String newOutputFilePath4 = "/Users/bekzatkalzan/Desktop/METCS622_APT/Homework2/src/searchResults4.json";
        
        // Call the file merging method
        FileMerger.mergeFiles(fileNames, outputFilePath);
        
        //Call the searching method
        KickstarterSearch.searchProjects("fitness", outputFilePath, newOutputFilePath1);
        KickstarterSearch.searchProjects("robot", outputFilePath, newOutputFilePath2);
        KickstarterSearch.searchProjects("wearable", outputFilePath, newOutputFilePath3);
        KickstarterSearch.searchProjects("wearable", outputFilePath, newOutputFilePath4);
        
        
        // Print search memory
        SearchMemory.printSearchMemory();
        
        // Print search frequency
        SearchMemory.printSearchFrequency();
        
        System.out.println("All tasks are completed.");
    }
}
