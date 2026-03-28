package Third10;

public class Question3 {
    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSubArray(new int[]{1}));
    }

    public static int maxSubArray(int[] arr) {
        int curr = arr[0];
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            curr = Math.max(arr[i], arr[i]+ curr);
            max = Math.max(curr,max);
        }
        return  max;
    }
}
