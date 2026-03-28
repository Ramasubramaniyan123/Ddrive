package First10;

public class Question10 {
    public static void main(String[] args) {
        Question10 question10 = new Question10();
        System.out.println(question10.maximumNumber(9669));
        System.out.println(question10.maximumNumber(9969));
        System.out.println(question10.maximumNumber(9699));


    }

    public int maximumNumber(int n) {
        char[] chars = String.valueOf(n).toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '6') {
                chars[i] = '9';
                break;
            }
        }
        return Integer.parseInt(new String(chars));
    }
}
