/*
Daniel DeMars
CSCI-310 Data Structures
Homework 17 – CPU Simulator
*/

import java.util.Comparator;
import java.util.Scanner;

public class SimulationRunner {
    public static void main(String[] args) {
        // Get the comparator from the user
        //Comparator<Job> comparator = getShedulingAlgorithm();

        // Ask the user if they want to use a file for the jobs
        //String input = getJobs(); 

        // Create CPU object 
        //CPUSimulator cpu = createCPU(input, comparator);

        Comparator<Job> comparator = new RRComparator(); 
        
        //CPUSimulator cpu = new CPUSimulator();
        //CPUSimulator cpu = new CPUSimulator(comparator);
        //CPUSimulator cpu = new CPUSimulator("test.txt", comparator);
        CPUSimulator cpu = new CPUSimulator("test.txt", "output.txt", comparator);
        
        cpu.start(); 
    }

    // Ask the user to select a scheduling algorithm
    protected static Comparator<Job> getShedulingAlgorithm() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do {
            // Prompt the user to select a scheduling algorithm
            System.out.println("Please select a scheduling algorithm (EX: For Round Robin, enter \"3\"):"
                + "\n1. First Come First Serve" 
                + "\n2. Highest Response Ratio" 
                + "\n3. Round Robin" 
                + "\n4. Shortest Job First" 
                + "\n5. Shortest Remaining Time \n");

            // Make sure the user enters a number between 1 and 5
            while (!scanner.hasNextInt()) {
                System.err.println("That's not a number! Please enter a number between 1 and 5.");
                scanner.next(); // discard non-integer input
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        // Return the comparator the user selected
        switch (choice) {
        case 1:
            return new FCFSComparator();
        case 2:
            return new HRRComparator();
        case 3:
            return new RRComparator();
        case 4:
            return new SJFComparator();
        case 5:
            return new SRTComparator();
        default:
            return null;
        }
        
    }

    // Ask the user if they want to use a file for the jobs or use the default commands
    protected static String getJobs(){
        Scanner scanner = new Scanner(System.in);
        String input = "";

        do {
            // Prompt the user to select a scheduling algorithm
            System.out.println("Would you like to use a file for the jobs? (Y/N):");
            input = scanner.nextLine();
            
            // Make sure the user enters "Y" or "N"
            if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N")) {
                System.err.println("Invalid input! Please enter \"Y\" or \"N\".");
            }
        } while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));
        
        // If the user wants to use a file, ask for the file name
        if (input.equalsIgnoreCase("Y")) {
            do {
                System.out.println("Please enter the name of the file (Must be .txt file):");
                input = scanner.nextLine();
                    
                // Check if the input is a .txt file
                if (!input.toLowerCase().endsWith(".txt")) {
                    System.err.println("\nInvalid file format! Please enter a .txt file.");
                }
            } while (!input.toLowerCase().endsWith(".txt"));
        } else {
            // If the user doesnt want to use a file, use DefaultCommands.txt
            return "DefaultCommands.txt"; 
        }
        return input;
    }

    // Create the CPU object
    protected static CPUSimulator createCPU(String inputfile, Comparator<Job> comparator){
        Scanner scanner = new Scanner(System.in);
        String outputMethod = "";

        // Ask user if they want to output to a file or the standard output device 
        do {
            System.out.println("Would you like to output to a file? "
                + "Choosing \"No\" results in output being printed in the standard output device (Y/N):");
            outputMethod = scanner.nextLine();

            // Make sure the user enters "Y" or "N"
            if (!outputMethod.equalsIgnoreCase("Y") && !outputMethod.equalsIgnoreCase("N")) {
                System.err.println("Invalid input! Please enter \"Y\" or \"N\".");
            }
        } while (!outputMethod.equalsIgnoreCase("Y") && !outputMethod.equalsIgnoreCase("N"));
        
        // If the user wants to use a file, ask for the file name
        if (outputMethod.equalsIgnoreCase("Y")) {
            do {
                System.out.println("Please enter the name of the file (Must be .txt file):");
                outputMethod = scanner.nextLine();
                    
                // Check if the input is a .txt file
                if (!outputMethod.toLowerCase().endsWith(".txt")) {
                    System.err.println("\nInvalid file format! Please enter a .txt file.");
                }
            } while (!outputMethod.toLowerCase().endsWith(".txt"));

            // create CPU object with user's chosen input method, output file, and scheduling algorithm
            return new CPUSimulator(inputfile, outputMethod, comparator);
        } else {
            // create CPU object with user's chosen input method and scheduling algorithm
            return new CPUSimulator(inputfile, comparator);
        }
    }
}
