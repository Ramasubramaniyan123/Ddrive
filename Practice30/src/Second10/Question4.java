package Second10;

public class Question4 {
    public static void main(String[] args) {
        System.out.println(removeDigit5(1234554321));
    }
    public  static int removeDigit5(int n){
        String removed = String.valueOf(n).replace("5","");
       return removed.isEmpty()? 0:Integer.parseInt(removed);
    }
}
