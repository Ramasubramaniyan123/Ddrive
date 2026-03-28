package Second10;

public class Question3 {
    public static boolean isValid(String s) {
        return s.matches("^\\d{3}-\\d{3}-\\d{4}$");
    }

    public static void main(String[] args) {
        System.out.println(isValid("123-456-7890")); // true
        System.out.println(isValid("1234567890"));   // false
        System.out.println(isValid("123-45-6789"));  // false
        System.out.println(isValid("abc-def-ghij")); // false
        System.out.println(isValid("123-456-78901"));// false
    }
}