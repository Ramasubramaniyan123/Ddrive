package Medium.Col2;
import java.util.*;
public class Triplets {
    static long triplets(long t, List<Integer> d) {
        Collections.sort(d);
        int n = d.size();
        long count = 0;

        for(int i = 0;i < n - 2;i++){
            int j = i + 1;
            int k = n - 1;
            while(j < k){
                long sum = (long) d.get(i) + d.get(j) + d.get(k);
                if(sum <= t){
                    count += (k - j);
                    j++;
                }
                else{
                    k--;
                }
            }
        }
        return count;

    }


}
