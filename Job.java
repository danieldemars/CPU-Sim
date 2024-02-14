/*
Daniel DeMars
CSCI-310 Data Structures
Homework 17 – CPU Simulator
*/
public class Job {
    protected String name; 
    protected int length;
    protected int startTime;
    protected int finishTime;
    protected int executionTime; 
    protected int waitTime;
    protected double responseRatio; 
    protected int remainingTime; 
    protected long rrPriority; 
    
    protected Job(String name, int length, int start){
        this.name = name;
        this.length = length; 
        this.startTime = start; 
        this.executionTime = 0;
    }
    
    public String toString(){
        String s = ""; 
        
        s += "Job " + this.name + "\n"; 
        s += "   Length: " + this.length + "\n"; 
        s += "   Start Time: " + this.startTime + "\n"; 
        s += "   Finish Time: " + this.finishTime + "\n"; 
        s += "   Execution Time: " + this.executionTime + "\n"; 
        s += "   Wait Time: " + this.waitTime + "\n"; 
        s += "   Response Ratio: " + String.format("%.1f", this.responseRatio) + "\n"; 
        
        return s; 
    }
}
