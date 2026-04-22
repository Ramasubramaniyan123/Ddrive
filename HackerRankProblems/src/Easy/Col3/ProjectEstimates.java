package Easy.Col3;

import java.util.Collections;
import java.util.List;

public class ProjectEstimates {
    public static int countPairs(List<Integer> projectCosts, int target) {
//        Set<Integer> set = new HashSet<>(projectCosts);
//        int res = 0;
//        for(int i : projectCosts){
//            if(set.contains(Math.abs(i - target))) res++;
//            if(set.contains(Math.abs(i  + target))) res++;
//        }
//        return res/2;
        Collections.sort(projectCosts);
        int i = 0;
        int j = 1;
        int n = projectCosts.size();
        int count = 0;
        while (j < n) {
            int diff = projectCosts.get(j) - projectCosts.get(i);
            if (diff == target) {
                count++;
                i++;
                j++;
            } else if (diff < target) {
                j++;
            } else {
                i++;
            }
        }
        return count;
    }
}
