package Third10;

import java.util.*;

class Question6 {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        int max = 1;
        int curr = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) continue;
            if (nums[i] == nums[i - 1] + 1) {
                curr++;
            } else {
                curr = 1;
            }

            max = Math.max(max, curr);
        }

        return curr;
    }
}