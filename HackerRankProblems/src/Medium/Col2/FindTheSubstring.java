package Medium.Col2;

public class FindTheSubstring {
    public static int firstOccurrence(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j = 0;

            while (j < m) {
                char pc = pattern.charAt(j);
                char tc = text.charAt(i + j);

                if (pc != '*' && pc != tc) break;

                j++;
            }

            if (j == m) return i;
        }

        return -1;
    }
}
