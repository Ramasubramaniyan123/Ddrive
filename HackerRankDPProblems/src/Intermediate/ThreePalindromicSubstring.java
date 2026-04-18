package Intermediate;

import java.util.Arrays;
import java.util.List;

public class ThreePalindromicSubstring {
    public static List<String> threePalindromicSubStrings(String word) {
        int n = word.length();
        boolean[][] isPal = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (word.charAt(i) == word.charAt(j)) {
                    if (j - i <= 2) {
                        isPal[i][j] = true;
                    } else {
                        isPal[i][j] = isPal[i + 1][j - 1];
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (!isPal[0][i]) continue;
            for (int j = i + 1; j < n; j++) {
                if (isPal[i + 1][j] && isPal[j + 1][n - 1]) {
                    return Arrays.asList(
                            word.substring(0, i + 1),
                            word.substring(i + 1, j + 1),
                            word.substring(j + 1)
                    );
                }
            }
        }
        return List.of("Impossible");
    }
}
