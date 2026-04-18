package Col17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfitTargets {
    public static int stockPairs(List<Integer> stocksProfit, int target) {
        Map<Long, Integer> map = new HashMap<>();
        for (int i : stocksProfit) {
            map.put((long) i, map.getOrDefault((long) i, 0) + 1);
        }

        int count = 0;

        for (long key : map.keySet()) {
            long val = target - key;
            if (!map.containsKey(val)) continue;
            if (key < val) count++;
            else if (key == val && map.get(key) >= 2) count++;
        }
        return count;
    }
}
