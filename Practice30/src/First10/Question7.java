package First10;

import java.util.HashSet;
import java.util.Set;

public class Question7 {
    public static void main(String[] args) {
        System.out.println(new Question7().uniqueThreeLetterSubstring("aabab"));
        System.out.println(new Question7().uniqueThreeLetterSubstring("aaaa"));
    }
    public  int uniqueThreeLetterSubstring(String string){
        Set<String> stringSet = new HashSet<>();
        for(int i = 0;i <= string.length()-3;i++){
            stringSet.add(string.substring(i,i+3));
        }
        return  stringSet.size();
    }
}
