package Third10;

class Question4 {
    public String makeEvenCounts(String s) {
        int c1 = 0, c2 = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') c1++;
            else c2++;
        }

        boolean remove1 = (c1 % 2 != 0);
        boolean remove2 = (c2 % 2 != 0);

        int r1Index = -1;
        int r2Index  = -1;
        if (remove1) {
            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == '1') {
                    r1Index = i;
                    break;
                }
            }
        }
        if (remove2) {
            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == '2') {
                    r2Index = i;
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (i == r1Index || i == r2Index) continue;
            result.append(s.charAt(i));
        }

        return result.toString();
    }
}