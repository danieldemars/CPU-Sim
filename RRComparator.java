
import java.util.Comparator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dande
 */
 
public class RRComparator implements Comparator<Job>{
    @Override
    public int compare(Job x, Job y){
        if (x.rrPriority < y.rrPriority){
            return -1;
        } else if (x.rrPriority > y.rrPriority) {
            return 1;
        } else {
            return 0; 
        }
    }
}
