package Day1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LongEncodedString {
    public static List<Integer> frequency(String s){
        int n = s.length();
        int i = 0;
        int [] freq= new int[26];
        while (i < n -2){
            int val;
            if(i + 2 < n && s.charAt(i+2) == '#'){
                val = (s.charAt(i) -'0' ) * 10 +  s.charAt(i+1) - '0';
                i+=3;
            }
            else{
                val = s.charAt(i) - '0';
                i++;
            }
            int count = 1;
            if (i< n && s.charAt(i) =='('){
                i++;
                count = 0;
                while (s.charAt(i) !=')'){
                    count = count * 10 + (s.charAt(i) - '0');
                    i++;
                }
                i++;
            }
            freq[val - 1] += count;
        }
        return Arrays.stream(freq).boxed().collect(Collectors.toList());
    }
}
