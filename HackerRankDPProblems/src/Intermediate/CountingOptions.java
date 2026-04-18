package Intermediate;

public class CountingOptions {
    public static int countOptions(int people, int groups) {
        int[][] dp = new int[people + 1][groups + 1];
        dp[0][0] = 1;

        for (int i = 1; i <= people; i++) {
            for (int j = 1; j <= groups; j++) {
                if (i < j) {
                    dp[i][j] = 0;
                    continue;
                }
                dp[i][j] = dp[i-1][j-1] + dp[i-j][j];
            }
        }
        return dp[people][groups];
    }
}
