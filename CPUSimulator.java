/*
Daniel DeMars
CSCI-310 Data Structures
Homework 17 – CPU Simulator
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CPUSimulator {
    private PrintStream output; 
    private BufferedReader input; 
    private ReadyQueue queue; 
    private String defaultCommands = "DefaultCommands.txt"; 
    private String[] timeSliceReports = new String[10];
    private String command = ""; 
    
    private int currentTimeSlice = 1;
    private int idleTime = 0;
    private int consecutiveIdleTime = 0;
    private int totalIdleTime = 0;
    private int busyTime = 0;
    private int runTime; 
    
    private boolean noNewCommands = false; 
    private int totalJobs = 0;
    private int totalJobsCompleted = 0;
    private String comparatorName;
    private ArrayList<Integer> waitTimes = new ArrayList<>(); 
    private ArrayList<Job> jobsCompleted = new ArrayList<>();
    private long rrMasterSequence = Long.MIN_VALUE; 
    
    public CPUSimulator(){
        // get input from DefaultCommands.txt
        try {
            input = new BufferedReader(new FileReader(defaultCommands));
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
            return;
        }
        
        // set output to standard output device
        output = System.out; 
        
        // create ready queue with SRT job schedueling 
        Comparator<Job> comparator = new SRTComparator(); 
        queue = new ReadyQueue(comparator); 
        
        // get name of comparator to display at the end 
        comparatorName = comparator.getClass().getSimpleName(); 
    }
    
    public CPUSimulator(Comparator<Job> comparator){
        // get input from DefaultCommands.txt
        try {
            input = new BufferedReader(new FileReader(defaultCommands));
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
        }
        
        // set output to standard output device
        output = System.out; 
        
        // create ready queue with job schedueling provided by user
        queue = new ReadyQueue(comparator); 
        
        // get name of comparator to display at the end 
        comparatorName = comparator.getClass().getSimpleName(); 
    }
    
    public CPUSimulator(String inFileName, Comparator<Job> comparator){
        // get input from file provided by the user 
        try {
            input = new BufferedReader(new FileReader(inFileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
            return;
        }
        
        // set output to standard output device
        output = System.out; 
        
        // create ready queue with job schedueling provided by user
        queue = new ReadyQueue(comparator); 
        
        // get name of comparator to display at the end 
        comparatorName = comparator.getClass().getSimpleName(); 
    }
    
    public CPUSimulator(String inFileName, String outFileName, Comparator<Job> comparator){
        // get input from file provided by user
        try {
            input = new BufferedReader(new FileReader(inFileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
        }
        
        // set output to file proivded by user
        try {
            File file = new File(outFileName);
            output = new PrintStream(file); 
        } catch (FileNotFoundException e){
            System.err.println("Error: File not found");
            return;
        }
        
        // create ready queue with job schedueling provided by user
        queue = new ReadyQueue(comparator);
        
        // get name of comparator to display at the end 
        comparatorName = comparator.getClass().getSimpleName(); 
    }
    
    public void start(){
        while (consecutiveIdleTime < 50){
            if (currentTimeSlice == timeSliceReports.length){ // make sure timeSliceReport array doesnt run out of space
                doubleArraySize(); 
            }
            
            sliceReportHeader(); // update slice report
            
            // get the next command from the user
            executeCommand(); 
            
            // execute job if queue isnt empty
            if (!queue.isEmpty()){
               executeJob();  
            }
            
            currentTimeSlice++; 
        }
        
        // close input file
        try {
            input.close(); 
        } catch (IOException e){
            
        }
        
        // print results
        printResults(); 
    }
    
    // read command from file and execute it 
    private void executeCommand(){
        try {
            command = input.readLine(); 
        } catch (IOException e){
            
        }
        
        if (command == null || command.equals("no new job this slice")){
            // if there is no new job and queue is empty, increase idle time 
            if (queue.isEmpty()){
                totalIdleTime++; 
                consecutiveIdleTime++; 
            }
            sliceReportNoNewJob(); // update slice report
            sliceReportEmpty(); // update slice report
        } else { // there is a new job
            consecutiveIdleTime = 0;
            
            sliceReportCommand(command); // update slice report
            
            // split up the command to get job information
            String[] splitCommands = command.split("\\s+");
            String jobName = splitCommands[2];
            int jobLength = Integer.parseInt(splitCommands[5]); 
                 
            // create a new job
            Job job = new Job(jobName, jobLength, currentTimeSlice); 
            job.startTime = currentTimeSlice; 
            job.remainingTime = job.length - job.executionTime;  
                    
            sliceReportAddedJob(job); // update slice report
            
            // add new job to the queue
            job.rrPriority = rrMasterSequence; 
            rrMasterSequence ++; 
            queue.add(job); 
            totalJobs++; 
        }
    }
    
    public void executeJob(){
        //  remove job at the top of the queue
        Job job = (Job)queue.remove(); 
        sliceReportRemoveJob(job); // update slice report
        
        // increment the wait time of every job that is still in the queue
        queue.incrementWaitTime();
        
        // execute the job (increment execution time)
        sliceReportExecution(job); // update slice report
        job.executionTime++;
        job.remainingTime = job.length - job.executionTime; 
         
        
        // increment busy time
        busyTime++; 
        
        // if job is not finished, add it back to the queue
        if (job.executionTime != job.length){
            job.rrPriority = rrMasterSequence; 
            queue.add(job); 
            rrMasterSequence ++;
            sliceReportAddedJob(job); // update slice report
        } else { // job is completed
            sliceReportCompleted(job); // update slice report
            totalJobsCompleted++; 
            waitTimes.add(job.waitTime);
            jobsCompleted.add(job); 
            job.finishTime = currentTimeSlice; 
        }
        
        job.responseRatio = (double)(job.waitTime + job.length) / job.length; 
        
        // if simulation is completed, get the runTime and idleTime 
        if (command == null && noNewCommands == false && totalJobsCompleted == totalJobs){
            runTime = currentTimeSlice;
            idleTime = totalIdleTime; 
            noNewCommands = true; 
        }
            
    }
    
    // update slice report with a header for each slice 
    private void sliceReportHeader(){
        timeSliceReports[currentTimeSlice] = "Time slice: " + currentTimeSlice + "\n";
    }
    
    // update slice report with command 
    private void sliceReportNoNewJob(){
        timeSliceReports[currentTimeSlice] += "   Command read: \"no new job this slice\"\n"; 
    }
    
    private void sliceReportCommand(String command){
        timeSliceReports[currentTimeSlice] += "   Command read: \"" + command + "\"\n";
    }
    
    // update slice report if the queue is empty
    private void sliceReportEmpty(){
        if (queue.isEmpty()){
            timeSliceReports[currentTimeSlice] += "   Ready queue empty, "
                + "nothing to execute, CPU idle for " + consecutiveIdleTime 
                + " continuous time slices" + "\n"; 
        }
    }
    
    // update slice report with information of job that is added to the queue
    private void sliceReportAddedJob(Job j){
        timeSliceReports[currentTimeSlice] += "   Adding to queue:    " + jobInformationString(j) + "\n"; 
    }
    
    // update slice report with information of job that is removed from the queue
    private void sliceReportRemoveJob(Job j){
        timeSliceReports[currentTimeSlice] += "   Removed from queue: " + jobInformationString(j) + "\n"; 
    }
    
    // update slice report with information of job that is being executed
    private void sliceReportExecution(Job j){
        timeSliceReports[currentTimeSlice] += "   Executing:          " + jobInformationString(j) + "\n"; 
    }
    
    // update slice report with information of job that is completed
    private void sliceReportCompleted(Job j){
        timeSliceReports[currentTimeSlice] += "   Completed:          " + jobInformationString(j) + "\n"; 
    }
    
    // returns string showing details of the job
    private String jobInformationString(Job j){
        String jobDisplayFormat = "[Name:%s Length:%d Execution:%d Remaining:%d Wait:%d]";
        return String.format(jobDisplayFormat, j.name, j.length, j.executionTime, j.remainingTime, j.waitTime);
    }
    
    private void printResults(){
        for (int i = 1; i < currentTimeSlice; i++){
            output.println(timeSliceReports[i]);
        }
        
        output.println("CPU idle for 50 time slices. Shutting Down.\n");
        output.println(summary());
        output.print(listOfJobs());
    }
    
    // summary of the CPU simulation
    private String summary(){
        String s = "";
        
        s += "----- Summary -----\n"; 
        s += "CPU Simulator with " + getComparatorString() + " Scheduling\n\n"; 
        s += "Simulation Run Time: " + runTime + "\n"; 
        s += "CPU Idle Time: " + idleTime + "\n";
        s += "CPU Busy Time: " + busyTime + "\n";
        s += "CPU Utilization: " + String.format("%.1f", ((double)busyTime / runTime) * 100) + "%\n";
        s += "Average Wait Time: " + String.format("%.1f", getAverageWaitTime()) + "\n";
        
        return s; 
    }
    
    // doubles the size of the timeSliceReports array
    private void doubleArraySize() {
        timeSliceReports = Arrays.copyOf(timeSliceReports, timeSliceReports.length * 2); 
    }
    
    // gets the name of the comparator used in the CPU as a string  
    private String getComparatorString(){
        if (comparatorName.equals("RRComparator")){
            return "Round Robin";
        } else if (comparatorName.equals("FCFSComparator")){
            return "First Come First Serve";
        } else if (comparatorName.equals("HRRComparator")){
            return "Highest Response Ratio";
        } else if (comparatorName.equals("SJFComparator")){
            return "Shortest Job First";
        } else {
            return "Shortest Remaining Time";
        }
    }
    
    // gets the average wait time of every job
    private double getAverageWaitTime(){
        double sumWaitTime = 0;
        for (Integer i : waitTimes){
            sumWaitTime += i; 
        }
        
        return sumWaitTime / waitTimes.size(); 
    }
    
    // gets the list of jobs in the order they were completed
    private String listOfJobs(){
        String s = "List of Jobs in Order of Completion\n";
        
        for (Job j : jobsCompleted){
            s += j.toString(); 
            s += "\n";
        }
        
        return s; 
    }
}