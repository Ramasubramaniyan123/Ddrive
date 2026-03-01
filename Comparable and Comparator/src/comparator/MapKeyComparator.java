package comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapKeyComparator {
    public static void main(String[] args) {
        Map<Integer, String> map = new TreeMap<>(
                Comparator.reverseOrder()
        );
        map.put(1, "Ram");
        map.put(2, "Vicky");
        map.put(3, "Lokesh");
        System.out.println(map);
    }
}
