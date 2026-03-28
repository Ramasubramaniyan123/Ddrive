public class MoveZeros {
    public static void main(String[] args) {
        int[] arr = {0,1,0,3,2};
        int index = 0;
        for(int i = 0; i < arr.length;i++){
            if(arr[i] != 0){
                arr[index] = arr[i];
                index++;
            }
        }
        for(int i = index;i < arr.length;i++){
            arr[i] = 0;
        }
        for(int i: arr){
            System.out.println(i);
        }
    }
}
