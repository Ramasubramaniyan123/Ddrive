package Medium.Col1;

import java.util.List;

public class DiskSpaceAnalysis {
    public static int segment(int x, List<Integer> space) {
        int n = space.size();
        int maxOfMin;
        int currentMin = Integer.MAX_VALUE;
        for (int i = 0; i < x; i++) {
            currentMin = Math.min(currentMin, space.get(i));
        }
        maxOfMin = currentMin;
        for (int i = x; i < n; i++) {
            int outgoing = space.get(i - x);
            int incoming = space.get(i);
            if (outgoing != currentMin) {
                currentMin = Math.min(currentMin, incoming);
            }
            else {
                currentMin = Integer.MAX_VALUE;
                for (int j = i - x + 1; j <= i; j++) {
                    currentMin = Math.min(currentMin, space.get(j));
                }
            }
            maxOfMin = Math.max(maxOfMin, currentMin);
        }
        return maxOfMin;
    }
}
