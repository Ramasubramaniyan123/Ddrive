package Third10;

import java.util.*;

public class Question5 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{1, 2, 2, 3, 4, 4, 4}, 2, 4))); // [2,1,3]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{5, 5, 5, 5}, 5, 5))); // [4]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{1, 2, 3}, 10, 20))); // []

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{1, 1, 2, 2, 3, 3}, 1, 3))); // [2,2,2]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{-3, -2, -2, -1, 0, 1}, -2, 0))); // [2,1,1]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{10, 20, 20, 30, 40, 50}, 15, 45))); // [2,1,1]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{7}, 5, 10))); // [1]

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{7}, 8, 10))); // []

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{}, 1, 5))); // []

        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{2, 2, 3, 4}, 2, 2))); // [2]

        // Bonus (order-sensitive if sorted keys used)
        System.out.println(Arrays.toString(frequencyInRange(
                new int[]{4, 2, 2, 3}, 2, 4))); // [2,1,1]
    }

    public static int[] frequencyInRange(int[] arr, int low, int high) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : arr) {
            map.put(x, map.getOrDefault(x,0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int key : map.keySet()) {
            if(key >= low && key <= high){
                list.add(map.get(key));
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }
}
