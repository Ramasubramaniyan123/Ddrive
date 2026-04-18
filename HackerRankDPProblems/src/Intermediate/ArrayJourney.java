package Intermediate;

import java.util.List;

public class ArrayJourney {
    public static long journey(List<Integer> path, int maxStep) {

        long[] dp = new long[path.size()];
        for (int i = 1; i < path.size(); i++) {
            long max = Long.MIN_VALUE;
            for (int j = Math.max(0, i - maxStep); j < i; j++) {
                max = Math.max(max, dp[j]);
            }
            dp[i] = max + path.get(i);
        }
        return dp[path.size() - 1];
    }
}
