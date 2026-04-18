package Intermediate;

import java.util.List;

public class ActiveFountain {
    public static int fountainActivation(List<Integer> locations){
        int n = locations.size();
        int[] arr = new int[n];
        for(int i = 0;i < n;i++){
            int left = Math.max(i - locations.get(i), 0);
            int right = Math.min(i + locations.get(i), n);
            arr[left] = Math.max(right, arr[left]);
        }
        int count = 0;
        int max = 0;
        int end = 0;
        for(int i = 0;i < n;i++){
             max = Math.max(arr[i],max);
             if(i > end){
                 end = max;
                 count++;
             }
        }
        return count;
    }
}
