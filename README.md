# CPU-Sim
This program simulates how a CPU determines which tasks to execute using various scheduling algorithms. 

The scheduling algorithm used, the list of jobs for the sim to complete, and where the output is displayed can be changed by editing the SimulationRunner.java file. 

This simulator supports the following scheduling algorithms:
First Come First Serve (FCFS) - Jobs are executed in the order they arrive. 
Highest Response Ratio (HRR) - Jobs are executed based on their response ratio. A job's response ratio = (job.WaitTime + job.Length) / job.Length.
Round Robin (RR) - Jobs are executed cyclically for one time slice each turn.
Shortest Job First (SJF) - The shortest jobs are executed first until they are completed.
Shortest Remaining Time (SRT) - Jobs are executed based on which one has the least remaining time. 
