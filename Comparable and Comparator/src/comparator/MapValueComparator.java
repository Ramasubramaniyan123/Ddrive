package comparator;

import java.util.*;

public class MapValueComparator {
    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>();
        map.put(101, "A");
        map.put(103, "C");
        map.put(102, "B");
        map.put(104, "D");
        List<Map.Entry<Integer, String>> list = new ArrayList<>(map.entrySet());
        list.sort((l1, l2) -> l1.getValue().compareTo(l2.getValue()));
        Map<Integer, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        System.out.println(sortedMap);
    }
}
