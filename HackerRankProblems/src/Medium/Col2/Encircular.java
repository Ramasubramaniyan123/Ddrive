package Medium.Col2;

import java.util.*;

public class Encircular {
    public static List<String> doesCircleExist(List<String> commands) {
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            int x = 0, y = 0;
            int dir = 0; // 0 = North

            for (char c : cmd.toCharArray()) {

                if (c == 'G') {
                    if (dir == 0) y++;
                    else if (dir == 1) x++;
                    else if (dir == 2) y--;
                    else x--;

                } else if (c == 'L') {
                    dir = (dir + 3) % 4;

                } else if (c == 'R') {
                    dir = (dir + 1) % 4;
                }
            }

            // KEY CHECK
            if ((x == 0 && y == 0) || dir != 0) {
                result.add("YES");
            } else {
                result.add("NO");
            }
        }

        return result;
    }
}
