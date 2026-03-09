package iterations;

public class BinaryGap {
    public static void main(String[] args) {
        System.out.println(binaryGap(1041));
        System.out.println(binaryGap(32));
        System.out.println(binaryGap(20));
    }
    public static int binaryGap(int n) {
        String string = Integer.toBinaryString(n);

        boolean firstOne = false;
        int count = 0;
        int max =0;
        for(char c:string.toCharArray()){
            if(c == '1' && !firstOne) firstOne = true;
            else if(firstOne && c == '0') {
                count ++;
            }
            else if( firstOne && c == '1'){
                if(count > max){
                    max = count;
                    count = 0;
                }
            }
        }
        return  max;
    }
}
