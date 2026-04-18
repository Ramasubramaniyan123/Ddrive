package Intermediate;

import java.util.Arrays;
import java.util.List;

public class MaxPathSum {
    public static int maxPathSum(List<List<Integer>> board, int p, int q) {
        int top = solveTop(board, p);
        int bottom = solveBottom(board, q);
        return Math.max(top, bottom);
    }

    public static int solveTop(List<List<Integer>> board, int startCol) {
        int rows = board.size();
        int cols = board.get(0).size();
        int[][] dp = new int[rows][cols];
        for (int[] row : dp) Arrays.fill(row, Integer.MIN_VALUE);
        dp[0][startCol] = board.get(0).get(startCol);

        for (int i = 1; i < rows; i++) {
            for(int j = 0;j < cols;j++){
                int best = dp[i-1][j];

                if(j - 1 >= 0){
                    best = Math.max(best,dp[i-1][j-1]);
                }
                if(j + 1 < cols){
                    best = Math.max(best, dp[i-1][j+1]);
                }
                dp[i][j] = best + board.get(i).get(j);

            }
        }
        int ans = Integer.MIN_VALUE;
        for (int j = 0; j < cols; j++) {
            ans = Math.max(ans, dp[rows - 1][j]);
        }

        return ans;

    }

    public static int solveBottom(List<List<Integer>> board, int startCol) {
        int rows = board.size();
        int cols = board.get(0).size();
        int[][] dp = new int[rows][cols];
        for(int[] row : dp){
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        dp[rows-1][startCol] = board.get(rows-1).get(startCol);

        for(int i = rows-2; i>=0 ;i--){
            for(int j = 0;j < cols;j++){
                int best = dp[i+1][j];

                if(j - 1 >= 0){
                    best = Math.max(best,dp[i+1][j-1]);
                }
                if(j + 1 < cols){
                    best = Math.max(best, dp[i+1][j+1]);
                }
                dp[i][j] = best + board.get(i).get(j);

            }
        }
        int ans = Integer.MIN_VALUE;
        for (int j = 0; j < cols; j++) {
            ans = Math.max(ans, dp[0][j]);
        }
        return ans;
    }
}
