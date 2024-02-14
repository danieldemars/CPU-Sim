
import java.util.Comparator;

public class SRTComparator implements Comparator<Job>{
    @Override
    public int compare(Job x, Job y){
        if (x.remainingTime < y.remainingTime){
            return -1;
        } else if (x.remainingTime > y.remainingTime){
            return 1; 
        } else {
            return 0; 
        }
    }
}
