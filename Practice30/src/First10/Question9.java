package First10;

public class Question9 {
    public static void main(String[] args) {
        Question9 obj = new Question9();

        // ✅ Basic
        System.out.println(obj.binaryReductionToZero("1") + " Expected: 1");
        System.out.println(obj.binaryReductionToZero("0") + " Expected: 0");
        System.out.println(obj.binaryReductionToZero("10") + " Expected: 2");
        System.out.println(obj.binaryReductionToZero("11") + " Expected: 3");
        System.out.println(obj.binaryReductionToZero("101") + " Expected: 5");
        System.out.println(obj.binaryReductionToZero("1101") + " Expected: 6");

        // ✅ Powers of 2
        System.out.println(obj.binaryReductionToZero("100") + " Expected: 3");
        System.out.println(obj.binaryReductionToZero("1000") + " Expected: 4");

        // ✅ All ones
        System.out.println(obj.binaryReductionToZero("111") + " Expected: 5");
        System.out.println(obj.binaryReductionToZero("1111") + " Expected: 7");

        // ✅ Mixed
        System.out.println(obj.binaryReductionToZero("1100101") + " Expected: 11");
        System.out.println(obj.binaryReductionToZero("1011101001") + " Expected: 16");

        // 🔥 Large all ones (400k)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 400000; i++) sb.append('1');

        long start = System.currentTimeMillis();
        int result = obj.binaryReductionToZero(sb.toString());
        long end = System.currentTimeMillis();

        System.out.println("Large all ones → " + result + " Expected: 799999");
        System.out.println("Time: " + (end - start) + " ms");

        // ⚠️ Large mixed (may be slow)
        sb = new StringBuilder();
        for (int i = 0; i < 200000; i++) sb.append('1');
        for (int i = 0; i < 200000; i++) sb.append('0');

        start = System.currentTimeMillis();
        result = obj.binaryReductionToZero(sb.toString());
        end = System.currentTimeMillis();

        System.out.println("Large mixed → " + result);
        System.out.println("Time: " + (end - start) + " ms");
    }
    public int binaryReductionToZero(String string){
        if (string.indexOf('0') == -1) {
            return 2 * string.length() - 1;
        }
        int count = 0;
        StringBuilder sb = new StringBuilder(string);

       while(!(sb.length() == 1 && sb.charAt(0) == '0')){
           int i = sb.length() -1;
            if(sb.charAt(i) == '1'){
                sb.setCharAt(i, '0');
            }
            else{
                sb.deleteCharAt(i);
            }
           count++;
        }
        return count;
    }
}
