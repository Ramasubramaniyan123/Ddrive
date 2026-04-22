package Easy.Col3;

public class GameWinner {
    public static String gameWinner(String colors) {
        int w = 0, b = 0;
        int n = colors.length();
        for (int i = 1; i < n - 1; i++) {
            char c = colors.charAt(i);
            if (c == 'w' && colors.charAt(i - 1) == 'w' && colors.charAt(i + 1) == 'w') w++;
            if (c == 'b' && colors.charAt(i - 1) == 'b' && colors.charAt(i + 1) == 'b') b++;
        }
        return w > b ? "wendy" : "bob";
    }
}
