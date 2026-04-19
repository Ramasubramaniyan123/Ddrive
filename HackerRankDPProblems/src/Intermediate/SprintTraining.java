package Intermediate;

import java.util.List;

public class SprintTraining {
    public static int getMoreVisited(int n, List<Integer> sprints){
        int[] count = new int[n + 1];
        for(int i = 1;i < sprints.size();i++){
            int start = sprints.get(i - 1);
            int end = sprints.get(i);
            if(start <= end){
                for(int j = start;j <= end;j++) count[j]++;
            }
            else{
                for(int j = start;j >= end; j--) count[j]++;
            }
        }
        int max = 0;
        int ans = 1;
        for(int i =1 ;i <= n;i++){
            if(count[i] > max){
                max = count[i];
                ans = i;
            }
        }
        return ans;
    }
}
