import java.util.HashMap;

import java.util.*;
public class StringOccurence {
    public static void main(String[] args){
        String string = "aaabbbccccab";
        Map<Character, Integer> map = new LinkedHashMap<>();

        for(char ch: string.toCharArray()) {
            map.put(ch, map.getOrDefault(ch,0)+1);
        }

        StringBuilder sb = new StringBuilder();
        for(char ch: map.keySet()){
            sb.append(ch).append(map.get(ch));
        }

        System.out.println(sb.toString());
    }
}
