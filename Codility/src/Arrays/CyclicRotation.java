package Arrays;

import java.util.Arrays;

public class CyclicRotation {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(cyclicRotation(new int[]{1, 2, 3, 4, 5, 6}, 6)));

    }

    public static int[] cyclicRotation(int[] arr, int k) {
        int n = arr.length;
        k = k % n;

        for(int i=0;i< k;i++){
            int last = arr[n-1];
            for(int j = n-1;j>0;j--){
                arr[j] = arr[j-1];
            }
            arr[0] = last;
        }
        return  arr;
    }
}
