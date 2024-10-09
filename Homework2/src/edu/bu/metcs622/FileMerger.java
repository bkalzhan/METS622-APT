package edu.bu.metcs622;

import java.io.*;

public class FileMerger {

    public static void mergeFiles(String[] fileNames, String outputFilePath) {
        File mergedFile = new File(outputFilePath);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(mergedFile))) {
            for (String fileName : fileNames) {
                File file = new File(fileName);
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    // Read each file line by line and write to the output file
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                        bw.newLine();  // Add a new line to separate JSON objects
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + fileName);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to merged file.");
            e.printStackTrace();
        }

        System.out.println("Merging completed successfully.");
    }
}
