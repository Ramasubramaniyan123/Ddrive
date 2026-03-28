package First10;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Question5 {
    public static void main(String[] args) {
            Question5 question5 = new Question5();
        System.out.println(question5.largestValueEqualToFrequency(new int[]{1,1,2,3,4}));
    }

    public int largestValueEqualToFrequency(int[] arr) {
            Map<Integer, Integer> map = new LinkedHashMap<>();

            for (int i : arr) {
                map.put(i, map.getOrDefault(i, 0) + 1);
            }

            int result = 0;

            for(Map.Entry<Integer,Integer> entry : map.entrySet()){
                int key = entry.getKey();
                int val = entry.getValue();
                if(key == val ){
                    result = Math.max(result,key);
                }
            }
            return result;
    }
}
