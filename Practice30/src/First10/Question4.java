package First10;

public class Question4 {
    public static void main(String[] args) {
        System.out.println(Question4.minimumSwapsToGroupRedBalls(new String("RWRWR")));
        System.out.println(Question4.minimumSwapsToGroupRedBalls(new String("RWWRWRR")));
    }
    public static int minimumSwapsToGroupRedBalls(String s){
        int max  = 0;
        int count = 0;
        boolean firstR = false;
        for(char c: s.toCharArray()){
            if(c == 'R') firstR = true;
            else if(firstR && c == 'W'){
                count++;
                if(max < count){
                    max = count;
                }
            }
        }
        return max;
    }
}
