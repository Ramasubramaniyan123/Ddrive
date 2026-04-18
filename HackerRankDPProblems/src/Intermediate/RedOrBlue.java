package Intermediate;

import java.util.ArrayList;
import java.util.List;

public class RedOrBlue {
    public static List<Long> redOrBlue(List<Integer> red, List<Integer> blue, int blueCost) {

        int n = red.size();
        long[] dpR = new long[n + 1];
        long[] dpB = new long[n + 1];
        dpR[0] = 0;
        dpB[0] = blueCost;
        List<Long> ans = new ArrayList<>();
        ans.add(0L);
        for (int i = 1; i <= n; i++) {
            long sR = dpR[i - 1] + red.get(i - 1);
            long stR = dpB[i - 1] + red.get(i - 1);
            dpR[i] = Math.min(sR, stR);

            long sB = dpB[i - 1] + blue.get(i - 1);
            long stB = dpR[i - 1] + blueCost + blue.get(i - 1);
            dpB[i] = Math.min(sB, stB);

            ans.add(Math.min(dpB[i], dpR[i]));
        }
        return ans;
    }
}
