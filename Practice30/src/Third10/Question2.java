package Third10;

public class Question2 {
    public static void main(String[] args) {
        System.out.println(validPhoneNumber("123-241-432"));
        System.out.println(validPhoneNumber("123-24-432"));
        System.out.println(validPhoneNumber("12a-241-432"));
        System.out.println(validPhoneNumber("123-241-4321"));
        System.out.println(validPhoneNumber("123241432"));
    }
    public static String validPhoneNumber(String phoneNumber){

        if(phoneNumber.length()!=11) return "INVALID";
        if(phoneNumber.charAt(3) != '-' || phoneNumber.charAt(7) != '-') return "INVALID";

        for(int i = 0;i < 11;i++){
            if(i ==3 || i == 7) continue;
            if(!Character.isDigit(phoneNumber.charAt(i))){
                return "INVALID";
            }
        }
        return "VALID";
    }
}
