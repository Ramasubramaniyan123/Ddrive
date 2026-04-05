package col11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinimumLengthSubarray {
    public  static int findMinimumLengthSubarray(List<Integer> arr, int k){

        Map<Integer,Integer> map = new HashMap<>();
        int left = 0;
        int minlen = Integer.MAX_VALUE;

        for(int right = 0; right < arr.size();right++){
            Integer value = arr.get(right);

            map.put(value,map.getOrDefault(value,0) + 1);

            while(map.size()>=k){
                minlen = Math.min(right - left + 1, minlen);
                int leftval  = arr.get(left);
                map.put(leftval,map.get(leftval)-1);
                if(map.get(leftval) == 0){
                    map.remove(leftval);
                }
                left++;
            }
        }
        return minlen == Integer.MAX_VALUE?-1:minlen;
    }
}
