package Second10;

import java.util.HashSet;
import java.util.Set;

public class Question10 {
    public static void main(String[] args) {
        System.out.println(Question10.distinctSubstringVaryingLength("aaaab",2));
    }
    static  int distinctSubstringVaryingLength(String s, int l){
        Set<String> set = new HashSet<>();
        for(int i = 0;i <= s.length() - l;i++){
            set.add(s.substring(i,i+l));
        }
        return  set.size();
    }
}
