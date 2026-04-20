package Medium.Col1;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinimumSum {
    public static int minSum(List<Integer> num, int k) {
        Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());

        for(int a:num) queue.add(a);

        while(k-- > 0){
            int value = queue.poll();
            value = (value + 1) /2;
            queue.add(value);
        }
        int sum = 0;
        for(int a: queue) sum+=a;

        return sum;

    }

}
