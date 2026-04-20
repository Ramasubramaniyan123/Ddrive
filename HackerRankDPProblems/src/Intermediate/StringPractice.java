package Intermediate;

import java.util.Scanner;

public class StringPractice {
    public static int longestPalindrome(int n, String s) {
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                } else {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        String s = sc.next();

        int result = longestPalindrome(n, s);
        System.out.println(result);
        sc.close();
    }
}
