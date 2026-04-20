package Medium.Col2;

public class ApproximateMatching {
    public static String calculateScore(String text, String prefixString, String suffixString) {

        int n = text.length();
        String answer = "";
        int maxScore = -1;

        // Try all substrings
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                String sub = text.substring(i, j + 1);

                int prefixScore = getPrefixScore(sub, prefixString);
                int suffixScore = getSuffixScore(sub, suffixString);

                int score = prefixScore + suffixScore;

                if (score > maxScore || (score == maxScore && sub.compareTo(answer) < 0)) {
                    maxScore = score;
                    answer = sub;
                }
            }
        }

        return answer;
    }

    // suffix of prefixString matches prefix of sub
    private static int getPrefixScore(String sub, String prefixString) {
        int maxLen = Math.min(sub.length(), prefixString.length());

        for (int len = maxLen; len >= 1; len--) {
            String suffix = prefixString.substring(prefixString.length() - len);
            String prefix = sub.substring(0, len);

            if (suffix.equals(prefix)) {
                return len;
            }
        }
        return 0;
    }

    // prefix of suffixString matches suffix of sub
    private static int getSuffixScore(String sub, String suffixString) {
        int maxLen = Math.min(sub.length(), suffixString.length());

        for (int len = maxLen; len >= 1; len--) {
            String prefix = suffixString.substring(0, len);
            String suffix = sub.substring(sub.length() - len);

            if (prefix.equals(suffix)) {
                return len;
            }
        }
        return 0;
    }
}
