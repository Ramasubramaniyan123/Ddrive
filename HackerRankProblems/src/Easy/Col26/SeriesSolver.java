package Easy.Col26;

import java.util.*;

public class SeriesSolver {
    public static String findOdd(List<String> series) {
        Map<String, List<String>> map = new HashMap<>();
        for(String s: series){
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i < s.length() - 1;i++){
                int diff = s.charAt(i+ 1) - s.charAt(i);
                sb.append(diff);
            }
            map.computeIfAbsent(sb.toString(),k -> new ArrayList<>()).add(s);
        }
        for(Map.Entry<String,List<String>> entry: map.entrySet()){
            if(entry.getValue().size() == 1){
                return entry.getValue().getFirst();
            }
        }
        return "";
    }
}
