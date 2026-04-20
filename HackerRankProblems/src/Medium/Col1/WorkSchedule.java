package Medium.Col1;

import java.util.ArrayList;
import java.util.List;

public class WorkSchedule {
    public static List<String> findSchedules(int workHours, int dayHours, String pattern) {
        List<String> result = new ArrayList<>();
        char[] arr = pattern.toCharArray();

        int fixedSum = 0;
        List<Integer> qIndex = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (arr[i] == '?') {
                qIndex.add(i);
            } else {
                fixedSum += arr[i] - '0';
            }
        }

        int remaining = workHours - fixedSum;

        backtrack(arr, qIndex, 0, remaining, dayHours, result);

        return result;
    }

    private static void backtrack(char[] arr, List<Integer> qIndex, int pos, int remaining, int dayHours, List<String> result) {

        // If all '?' filled
        if (pos == qIndex.size()) {
            if (remaining == 0) {
                result.add(new String(arr));
            }
            return;
        }

        // Pruning
        int remainingSlots = qIndex.size() - pos;

        int index = qIndex.get(pos);

        for (int val = 0; val <= dayHours; val++) {
            arr[index] = (char) (val + '0');
            backtrack(arr, qIndex, pos + 1, remaining - val, dayHours, result);
        }
    }
}
