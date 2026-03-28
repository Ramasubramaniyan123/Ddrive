package First10;

public class Question2 {
    public static void main(String[] args) {
        System.out.println(new Question2().increaseNumberAlmostK("1234",2));
        System.out.println(new Question2().increaseNumberAlmostK("9875",1));
    }
    public  String increaseNumberAlmostK(String string, int k){
        int i = 0;
        StringBuilder sb = new StringBuilder(string);
        while( k > 0){
            if(sb.charAt(i) != '9') {
                sb.setCharAt(i, '9');
                k--;
            }
            i++;
        }
        return  sb.toString();
    }
}
