package Intermediate;

import java.util.List;

public class MinimumProcessingTime {
    public static int getMinProcessingTime(List<Integer> data, int processTimeA, int processTimeB){
        int sum = data.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return 0;
    }
}
