/*
Daniel DeMars
CSCI-310 Data Structures
Homework 17 – CPU Simulator
*/

import java.util.ArrayList;
import java.util.Comparator;

public class ReadyQueue extends Heap<Job>{
    public ReadyQueue(Comparator<Job> comparator){
        super(comparator);
    }
    
    protected void incrementWaitTime(){
        ArrayList<Job> jobs = new ArrayList<>(); 
        
        // empty heap and put jobs into list
        while(!this.isEmpty()){
            jobs.add((Job)this.remove()); 
        }
        
        // increment waitTime for every job in list and put it back into queue
        for (Job j : jobs){
            j.waitTime++; 
            this.add(j); 
        }
    }
}