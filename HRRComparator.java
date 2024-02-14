
import java.util.Comparator;

public class HRRComparator implements Comparator<Job>{
    @Override
    public int compare(Job x, Job y){
        double xResponseRatio = (x.waitTime + x.length) / (double)x.length; 
        double yResponseRatio = (y.waitTime + y.length) / (double)y.length; 
        
        if (xResponseRatio > yResponseRatio){
            return -1;
        } else if (xResponseRatio < yResponseRatio){
            return 1; 
        } else {
            return 0; 
        }
    }
}
