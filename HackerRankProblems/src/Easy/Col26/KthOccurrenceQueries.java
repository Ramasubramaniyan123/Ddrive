package Easy.Col26;

import java.util.ArrayList;
import java.util.List;

public class KthOccurrenceQueries {
    public static List<Integer> kthOccurrence(int X, List<Integer> arr, List<Integer> queryValues) {
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == X) {
                pos.add(i + 1);
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int q : queryValues) {
            if (q <= pos.size()) {
                res.add(pos.get(q - 1));
            } else {
                res.add(-1);
            }
        }

        return res;
    }

}
