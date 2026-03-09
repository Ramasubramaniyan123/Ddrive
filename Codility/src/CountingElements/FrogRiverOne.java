package CountingElements;

public class FrogRiverOne {
    public static void main(String[] args) {
        System.out.println(new FrogRiverOne().frogRiverOne(new int[]{1,3,1,4,2,3,5,5}, 5));
    }

    public int frogRiverOne(int[] arr, int x) {
        boolean[] booleans = new boolean[x + 1];
        int count = 0;

        for (int a = 0; a < arr.length; a++) {
            int position = arr[a];
            if (!booleans[position]) {
                booleans[position] = true;
                count++;
            }
            if (count == x) {
                return a;
            }
        }
        return  -1;
    }
}
