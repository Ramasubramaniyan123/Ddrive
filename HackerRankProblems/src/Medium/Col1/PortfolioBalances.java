package Medium.Col1;

import java.util.List;

public class PortfolioBalances {
    public static long maxValue(int n, List<List<Integer>> rounds) {
        long arr[] = new long[n + 1];
        for(int i = 0;i < rounds.size();i++){
            List<Integer> list = rounds.get(i);
            int left = list.get(0) - 1;
            int right = list.get(1) - 1;
            int cont = list.get(2);
            arr[left]+=cont;

            if(right + 1 < n){
                arr[right+1] -=cont;
            }

        }
        long max = 0;
        long sum = 0;

        for(int i = 0;i < n;i++){
            sum+=arr[i];
            max = Math.max(sum, max);
        }
        return max;
    }
}
