package CountingElements;

public class PermCheck {

    public static void main(String[] args) {
        System.out.println(new PermCheck().solution(new int[]{4,1,2,3}));
    }
    public int solution(int[] A) {
        int n = A.length;
        boolean [] booleans =  new boolean[n+1];
        for(int num:A){
            if(num < 1 || num > n || booleans[num]) return 0;
            booleans[num]= true;
        }
        return 1;
    }
}
