/*
Daniel DeMars
CSCI-310 Data Structures
Homework 17 – CPU Simulator
*/

import java.util.Comparator;

public class SimulationRunner {
    public static void main(String[] args) {
        Comparator<Job> comparator = new RRComparator(); 
        
        CPUSimulator cpu = new CPUSimulator();
        //CPUSimulator cpu = new CPUSimulator(comparator);
        //CPUSimulator cpu = new CPUSimulator("test.txt", comparator);
        //CPUSimulator cpu = new CPUSimulator("test.txt", "output.txt", comparator);
        
        cpu.start(); 
    }
}
