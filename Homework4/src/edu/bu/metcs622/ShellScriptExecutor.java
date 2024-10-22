package edu.bu.metcs622;

import java.io.File;
import java.io.IOException;

public class ShellScriptExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
	        // Specify the shell script file
	        File scriptFile = new File("find_duplicates.sh");

	        // Make sure the script has executable permissions
	        scriptFile.setExecutable(true);

	        // Create the process to run the shell script
	        ProcessBuilder processBuilder = new ProcessBuilder("../find_duplicates.sh");

            // Redirect output and error streams to console
            processBuilder.inheritIO();

	        // Start the process
	        Process process = processBuilder.start();
	        
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Shell script executed with exit code: " + exitCode);

		} catch(IOException e) {
            System.err.println("Error executing the script: " + e.getMessage());
            e.printStackTrace();
		} catch (InterruptedException e) {
            System.err.println("Script execution was interrupted: " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
	}

}
