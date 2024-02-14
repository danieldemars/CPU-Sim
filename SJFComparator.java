
import java.util.Comparator;

public class SJFComparator implements Comparator<Job>{
    @Override
    public int compare(Job x, Job y){
        if (x.length < y.length){
            return -1;
        } else if (x.length > y.length){
            return 1;
        } else {
            return 0; 
        }
    }
}
