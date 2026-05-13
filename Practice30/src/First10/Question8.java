package First10;

import java.util.*;

public class Question8 {
    public static void main(String[] args) {
        System.out.println(buildAllPalindrome("kkj"));
        System.out.println(buildAllPalindrome("rammarr"));
    }
    public static List<String> buildAllPalindrome(String string){
        int[] freq = new int[26];
        List<String > result = new ArrayList<>();
        for(char c : string.toCharArray()){
            freq[c - 'a']++;
        }
        int oddCount = 0;
        char middle = 0;
        for(int i = 0; i< 26;i++){
            if(freq[i] % 2 == 1) {
                oddCount ++;
                middle = (char) (i + 'a');
            }
        }
        if(oddCount> 1){
            return  result;
        }
        StringBuilder half = new StringBuilder();
        for(int i = 0; i < 26;i++){
            for(int j = 0; j < freq[i] /2 ;j++){
                half.append((char) ( i + 'a'));
            }
        }
        List<String> per = permutations(half.toString().toCharArray(),0);
        Set<String> set = new HashSet<>(per);
        for(String left : set){
            String right = new StringBuilder(left).reverse().toString();
            if(oddCount == 1) result.add(left+middle+right);
            else result.add(left+right);
        }


        return  result;
    }
    private static List<String> permutations(char[] strings, int fixedIndex){
        List<String> stringList = new ArrayList<>();
        if(fixedIndex == strings.length - 1){
            stringList.add(new String(strings));
            return stringList;
        }
        for(int i = fixedIndex;i < strings.length; i++){
            swap(strings,fixedIndex,i);
            List<String> list = permutations(strings,fixedIndex+1);
            stringList.addAll(list);
            swap(strings,fixedIndex,i);
        }
        return stringList;
    }
    private static void swap(char [] chars , int fixedIndex, int i){
        char temp = chars[fixedIndex];
        chars[fixedIndex] = chars[i];
        chars[i] = temp;
    }
}
