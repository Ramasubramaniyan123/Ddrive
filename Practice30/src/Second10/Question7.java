package Second10;

public class Question7 {
    public static void main(String[] args) {
        System.out.println(Question7.reverse(123));
        System.out.println(Question7.reverse(-321));
        System.out.println(Question7.reverse(120));
        System.out.println(Question7.reverse(1534236469));

    }
    public static int reverse(int x){
        int rev = 0;
        while (x!=0){
                int digit = x % 10;
            if (rev > Integer.MAX_VALUE / 10) return 0;
            if (rev < Integer.MIN_VALUE / 10 ) return 0;
                rev = rev * 10 + x % 10;
                x /= 10;
        }
        return  rev;
    }
}
