package Third10;

import java.util.*;

class Question8 {
    public List<List<Integer>> twoSumAllPairs(int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                for (int index : map.get(complement)) {
                    result.add(Arrays.asList(index, i));
                }
            }
            map.putIfAbsent(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }

        return result;
    }
}