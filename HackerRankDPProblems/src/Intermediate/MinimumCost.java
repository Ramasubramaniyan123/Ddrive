package Intermediate;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MinimumCost {
    public static long getMinimumCost(List<Integer> cost, int k) {
        int n = cost.size();
        long[] dp = new long[n + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        for (int i = 1; i <= n; i++) {
            while (!queue.isEmpty() && queue.peekFirst() < i - k) queue.pollFirst();
            dp[i] = dp[queue.peekFirst()] + cost.get(i - 1);
            while (!queue.isEmpty() && dp[queue.peekLast()] >= dp[i]) queue.pollLast();
            queue.add(i);
        }
        return dp[n];
    }
}
