package Second10;

public class Question3 {
    public static boolean isValid(String s) {
        return s.matches("^\\d{3}-\\d{3}-\\d{4}$");
    }

    public static void main(String[] args) {
        System.out.println(isValid("123-456-7890"));
        System.out.println(isValid("1234567890"));
        System.out.println(isValid("123-45-6789"));
        System.out.println(isValid("abc-def-ghij"));
        System.out.println(isValid("123-456-78901"));
    }
}