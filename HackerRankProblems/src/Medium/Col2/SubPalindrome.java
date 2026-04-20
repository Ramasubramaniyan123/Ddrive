package Medium.Col2;

import java.util.HashSet;
import java.util.Set;

public class SubPalindrome {
    public static int palindrome(String s) {
        int n = s.length();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            expand(s, i, i, set);
            expand(s, i, i + 1, set);
        }
        return set.size();
    }

    public static void expand(String s, int left, int right, Set<String> set) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            set.add(s.substring(left, right + 1));
            left--;
            right++;
        }
    }
}
