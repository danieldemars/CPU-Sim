
import java.util.Comparator;

public class FCFSComparator implements Comparator<Job>{
    @Override
    public int compare(Job x, Job y){
        if (x.startTime < y.startTime){
            return -1;
        } else if (x.startTime > y.startTime){
            return 1; 
        } else {
            return 0; 
        }
    }
}
