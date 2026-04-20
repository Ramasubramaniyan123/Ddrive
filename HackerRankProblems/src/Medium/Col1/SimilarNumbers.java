package Medium.Col1;

public class SimilarNumbers {
    public static long findSimilar(String a, String b){
        if(same(a,b)){
            return count(a);
        }
        else {
            return  count(b);
        }

    }
    public  static  boolean same(String a, String b){
        if(a.length()!= b.length()) return  false;
        int [] freq = new int[10];
        for(char i: a.toCharArray()) freq[i - '0']++;
        for (char i: b.toCharArray()) freq[i - '0']--;
        for(int i:freq) if(i>0) return  false;
        return  true;


    }
    public static long count(String string){
        int[] freq = new int[10];
        for(char i: string.toCharArray()) freq[i - '0']++;

        int n = string.length();
        long total = factorial(n);
        for(int i: freq){
            if(i > 1) total= total/factorial(i);
        }
        if(freq[0] > 0){
            freq[0] --;
            long invalid = factorial(n-1);
            for(int i:freq){
                if(i>1)invalid = invalid/factorial(i);
            }
            total -=invalid;
        }
        return  total;
    }
    public static long factorial(int num){
        long res = 1;
        for(int i = 2;i <= num;i++){
            res*=i;

        }
        return  res;
    }
}
