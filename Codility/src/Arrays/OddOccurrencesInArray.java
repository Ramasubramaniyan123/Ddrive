package Arrays;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class OddOccurrencesInArray {
    public static void main(String[] args) {
        OddOccurrencesInArray oddOccurrencesInArray = new OddOccurrencesInArray();
        System.out.println(oddOccurrencesInArray.oddOccurrencesInArray(new int[]{9,9,1,2,1,2,3,}));
        System.out.println(oddOccurrencesInArray.oddOccurrencesInArrayEfficient(new int[]{99,1,2,3,3,4,4,1,2,}));
    }
    public int oddOccurrencesInArray(int [] arr){
        Map<Integer,Integer> map = new HashMap<>();
        for(int a:arr){
            map.put(a,map.getOrDefault(a,0)+1);
        }
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(entry.getValue() % 2 !=0){
                return  entry.getKey();
            }
        }
        return  -1;
    }
    public int oddOccurrencesInArrayEfficient(int[] arr) {
        int result = 0;
        for (int a : arr) {
            result ^= a;
        }
        return result;
    }
}
