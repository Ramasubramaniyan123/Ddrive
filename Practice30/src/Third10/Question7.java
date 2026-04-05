package Third10;



import java.util.*;

public class Question7 {
    public static void main(String[] args) {
        System.out.println(countPalindromes("baba"));   // 2
        System.out.println(countPalindromes("aaaa"));   // 1
        System.out.println(countPalindromes("abc"));    // 0
    }

    public static int countPalindromes(String string){
        int[] freq = new int[26];

        for(char c : string.toCharArray()){
            freq[c - 'a']++;
        }

        int oddCount = 0;
        char middle = 0;

        for(int i = 0; i < 26; i++){
            if(freq[i] % 2 == 1){
                oddCount++;
                middle = (char)(i + 'a');
            }
        }

        if(oddCount > 1){
            return 0;
        }

        StringBuilder half = new StringBuilder();
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < freq[i] / 2; j++){
                half.append((char)(i + 'a'));
            }
        }
        List<String> per = permutations(half.toString().toCharArray(), 0);
        Set<String> set = new HashSet<>(per);
        int count = 0;

        for(String left : set){
            String right = new StringBuilder(left).reverse().toString();
            if(oddCount == 1){
                String palindrome = left + middle + right;
            } else {
                String palindrome = left + right;
            }
            count++;
        }

        return count;
    }

    private static List<String> permutations(char[] strings, int fixedIndex){
        List<String> stringList = new ArrayList<>();

        if(fixedIndex == strings.length - 1){
            stringList.add(new String(strings));
            return stringList;
        }

        for(int i = fixedIndex; i < strings.length; i++){
            swap(strings, fixedIndex, i);
            stringList.addAll(permutations(strings, fixedIndex + 1));
            swap(strings, fixedIndex, i);
        }

        return stringList;
    }

    private static void swap(char[] chars, int i, int j){
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}
