package Second10;

import java.util.Arrays;

public class Question2 {
    public static void main(String[] args) {
        System.out.println(validateArray(new int[]{1,1,4,4},4,2));
        System.out.println(validateArray(new int[]{2,3,2,3},3,2));
        System.out.println(validateArray(new int[]{1,2,3,4},4,1));
        System.out.println(validateArray(new int[]{1,1,1,4},4,2));
        System.out.println(validateArray(new int[]{1,2,3,5},4,1));
    }
    public static boolean validateArray(int[] values, int k ,int l){
        int n = values.length;
        Arrays.sort(values);
        for(int i = l;i< n;i++){
            if(values[i] == values[i - l] ){
                return false;
            }
        }
        return values[n-1] <= k;
    }
}
