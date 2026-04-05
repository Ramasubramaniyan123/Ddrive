package Day1;

import java.util.List;

public class DiskSpaceAnalysis {
    public static int segment(List<Integer>list , int x){
        int n = list.size();
        int maxOfMin;
        int curr = Integer.MAX_VALUE;
        for(int i = 0;i< x;i++){
            curr = Math.min(curr,list.get(i));
        }
        maxOfMin = curr;

        for(int i = x;i <n;i++){
            int incoming = list.get(i);
            int outgoing = list.get(i - x);

            if(outgoing != curr){
                curr = Math.min(curr,incoming);
            }
            else{
                curr = Integer.MAX_VALUE;

                for(int j = i -x +1 ; j<=i;j++){
                    curr = Math.min(curr,list.get(j));
                }
            }
            maxOfMin = Math.max(maxOfMin,curr);
        }
        return  maxOfMin;
    }
}
