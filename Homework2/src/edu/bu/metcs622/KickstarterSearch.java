package edu.bu.metcs622;

import java.io.*;

import org.json.JSONObject;

public class KickstarterSearch {

    // Modify this method to write the search results to a file
	public static void searchProjects(String keyword, String filePath, String outputFilePath) {
	    try {
	        File inputFile = new File(filePath);
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        String line;

	        // Record the search in memory
	        SearchMemory.addSearch(keyword);

	        // File to write the search results
	        File outputFile = new File(outputFilePath);
	        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

	        // Read file line by line
	        while ((line = br.readLine()) != null) {
	            // Convert to lower case for case-insensitive search
	            if (line.toLowerCase().contains(keyword.toLowerCase())) {
	                // Write the JSON line to the output file
	                bw.write(line);
	                bw.newLine();  // Add a newline to separate JSON objects
	            }
	        }

	        // Close the file writers and readers
	        bw.close();
	        br.close();

	        System.out.println("Search results saved to file: " + outputFilePath);

	        // Now print the project details from the output file
	        printProjectDetails(outputFilePath);
	        
	    } catch (IOException e) {
	        System.out.println("Error reading or writing file.");
	        e.printStackTrace();
	    }
	}
    
    // Method to print project details from a JSON file
    private static void printProjectDetails(String inputFilePath) {
        try {
            // Create a reader for the input file
            File inputFile = new File(inputFilePath);
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            String line;
            
            // Read the input file line by line (each line is a separate JSON object)
            while ((line = br.readLine()) != null) {
                // Parse the line as a JSON object
                JSONObject jsonObject = new JSONObject(line);
                JSONObject data = jsonObject.optJSONObject("data");

                // Extract the fields from the JSON object
                String name = data.optString("name", "N/A");
                double goal = data.optDouble("goal", 0.0);

                // The category is nested, so we need to check for that
                JSONObject category = data.optJSONObject("category");
                String categoryName = category != null ? category.optString("name", "N/A") : "N/A";

                // Print the required details
                System.out.println("Name: " + name);
                System.out.println("Goal: " + goal);
                System.out.println("Category: " + categoryName);
                System.out.println("---------------------------------------");
            }

            // Close the reader
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }
}

