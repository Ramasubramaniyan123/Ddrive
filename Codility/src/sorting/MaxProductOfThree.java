package sorting;

public class MaxProductOfThree {
    public static void main(String[] args) {
        System.out.println(maxProductOfThreeEfficient(  new int[]{-3, 1, 2, -2, 5, 6,}));
    }

    public static int maxProductOfThree(int[] arr) {
        if (arr.length < 3) return -1;
        int maxVal = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length - 2; i++) {
            for (int j = i; j < arr.length - 1; j++) {
                for (int k = j; k < arr.length; k++) {
                    int mul = arr[i] * arr[j] * arr[k];
                    maxVal = Math.max(mul, maxVal);
                }
            }
        }
        return maxVal;
    }

    public static int maxProductOfThreeEfficient(int[] arr) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;

        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int number : arr) {
            if (number > max1) {
                max3 = max2;
                max2 = max1;
                max1 = number;
            } else if (number > max2) {
                max3 = max2;
                max2 = number;
            } else {
                max3 = number;
            }

            if(number < min1){
                min2 = min1;
                min1 = number;
            }
            else {
                min2 = number;
            }
        }
        return Math.max(max1*max2*max3,min1*min2*max1);
    }
}
