package Intermediate;

import java.util.*;

public class ProfitAnalysis {
    public static long getMaxProfit(List<Integer> pnl, int k) {
        int n = pnl.size();
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + pnl.get(i);
        Deque<Integer> dq = new ArrayDeque<>();
        dq.add(0);
        long maxSum = Long.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() < i - k) dq.pollFirst();
            if(!dq.isEmpty()) maxSum = Math.max(maxSum, prefix[i] - prefix[dq.peekFirst()]);
            while (!dq.isEmpty() && prefix[dq.peekLast()] >= prefix[i]) dq.pollLast();
            dq.addLast(i);
        }
        return Math.max(maxSum,0);
    }
}
