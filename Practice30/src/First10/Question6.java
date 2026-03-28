package First10;

public class Question6 {
    public static void main(String[] args) {
        String s = "121212";
        System.out.println(LargestEvenCountInteger(s));
    }
    public static String LargestEvenCountInteger(String string){
        int count1 = 0, count2 = 0;

        for(char c : string.toCharArray()){
            if(c == '1') count1++;
            else  count2++;
        }

        if(count1 %2 ==0 && count2 % 2 ==0) return  string;

        StringBuilder sb = new StringBuilder(string);

        if(count1 % 2 ==1 && count2 %2 ==1){
            int first = sb.indexOf("1");
            int last = sb.lastIndexOf("2");
            if(  last !=-1 && last<sb.length()) sb.deleteCharAt(last);
            if(first!= -1) sb.deleteCharAt(first);

        }
        else if(count1 % 2 ==1){
            int first = sb.indexOf("1");
            if(first!=-1) sb.deleteCharAt(first);
        }
        else{
            int last = sb.lastIndexOf("2");
            if(last!=-1 && last< sb.length()) sb.deleteCharAt(last);
        }
        return sb.toString();
    }
}
