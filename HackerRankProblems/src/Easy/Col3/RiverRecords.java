package Easy.Col3;

import java.util.List;

public class RiverRecords {
    public static int maxTrailing(List<Integer> arr) {
        int minDiff = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.size(); i++) {
            int curr = arr.get(i);
            if (curr > min) minDiff = Math.max(minDiff, curr - min);
            else min = curr;
        }
        return minDiff;
    }
}
