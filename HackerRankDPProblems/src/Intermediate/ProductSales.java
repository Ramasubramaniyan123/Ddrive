package Intermediate;

import java.util.Collections;
import java.util.List;

public class ProductSales {
    public static long maximumProfit(List<Integer> inventory, int order){
        int max = Collections.max(inventory);
        long[] freq = new long[max+1];
        for(int a: inventory) freq[a] ++;
        long sum = 0;
        for(int i = max;i >= 0;i--){
            if(order>= freq[i]){
                sum += (i * freq[i]);
                order -= freq[i];
                freq[i-1] += freq[i];
            }
            else{
                sum += order * i;
                order = 0;
            }
            if(order == 0){
                break;
            }
        }
        return sum;
    }
}
