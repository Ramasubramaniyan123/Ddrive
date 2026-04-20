package Medium.Col1;

import java.util.ArrayList;
import java.util.List;

public class CountingBinarySubstrings {

    /*
     * Complete the function below.
     */
    static int counting(String s) {
        List<Integer> list = new ArrayList<>();

        int count = 1;

        for(int i = 1; i < s.length();i++){
            if(s.charAt(i) == s.charAt(i-1)){
                count++;
            }
            else {
                list.add(count);
                count = 1;
            }
        }
        list.add(count);

        int result = 0;

        for(int i = 1; i < list.size();i++){
            result+= Math.min(list.get(i) , list.get(i-1));
        }
        return  result;

    }

}
