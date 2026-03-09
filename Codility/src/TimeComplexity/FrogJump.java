package TimeComplexity;

public class FrogJump {
    public static void main(String[] args) {
        System.out.println(frogJump(10,85,30));
    }
    public static  int frogJump(int x,int y,int d){
        int mod = (y-x) % d;
        return mod!=0 ? (y-x)/d+1:(y-x)/d;
    }
}
