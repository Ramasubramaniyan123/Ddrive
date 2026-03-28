package Third10;

public class Question1 {
    public static void main(String[] args) {
        System.out.println(pricefluctuation(new int[]{1,3,2,4,5,3}));
    }
    public static int pricefluctuation(int[] arr){
        int count = 0;
        for(int i = 0; i < arr.length - 1;i++){
            if(arr[i] < arr[i+1]) count++;
        }
        return count;
    }
}
