package Easy.Col26;

import java.util.*;

public class BreakTheBricks {
    public static List<List<Long>> breakTheBricks(int bigHits, List<Integer> newtons) {
        List<List<Long>> res = new ArrayList<>();
        int n = newtons.size();
        List<Integer> sorted = new ArrayList<>(newtons);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(newtons.get(i), k -> new ArrayList<>()).add(i + 1);
        }
        Collections.sort(sorted);

        long total = 0;
        int big = bigHits;
        for (int i = n - 1; i >= 0; i--) {
            if (big > 0) {
                total++;
                big--;
            } else {
                total += sorted.get(i);
            }

        }
        List<Long> t = new ArrayList<>();
        t.add(total);
        List<Long> b = new ArrayList<>();
        List<Long> s = new ArrayList<>();
        int k = bigHits;
        for (int i = n - 1; i >= 0; i--) {
            int val = sorted.get(i);
            List<Integer> list = map.get(val);
            int idx = list.removeLast();
            if (k > 0) {
                b.add((long) idx);
                k--;
            } else {
                s.add((long) idx);
            }
        }
        Collections.sort(b);
        Collections.sort(s);
        if (b.isEmpty()) b.add(-1L);
        if (s.isEmpty()) s.add(-1L);
        res.add(t);
        res.add(b);
        res.add(s);

        return res;
    }
}
