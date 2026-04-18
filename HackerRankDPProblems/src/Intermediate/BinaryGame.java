package Intermediate;

public class BinaryGame {
    public static int countGoodStrings(int min_length, int max_length, int one_group, int zero_group){
        int[] dp = new int[max_length+1];
        dp[0] = 1;
        int res = 0;
        int MOD = 1_000_000_007;
        for(int i = 1;i <=max_length;i++){
            if(i - zero_group >= 0){
                dp[i] = (dp[i] + dp[i - zero_group]) % MOD;
            }
            if(i - one_group >= 0){
                dp[i] = (dp[i] + dp[i - one_group]) % MOD;
            }
            if(i >= min_length){
                res =( res + dp[ i]) % MOD;
            }
        }
        return res;
    }
}
