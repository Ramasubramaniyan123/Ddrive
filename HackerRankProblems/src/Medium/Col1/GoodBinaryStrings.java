package Medium.Col1;
import java.util.*;
public class GoodBinaryStrings {
    public static String largestMagical(String s) {
        Stack<List<String>> stack = new Stack<>();
        stack.push(new ArrayList<>());
        for(char c: s.toCharArray()){
            if(c == '1'){
                stack.push(new ArrayList<>());
            }
            else{
                List<String> top = stack.pop();
                Collections.sort(top,Collections.reverseOrder());
                StringBuilder sb = new StringBuilder();
                for(String str: top) sb.append(str);
                String curr = "1" + sb.toString() +"0";
                stack.peek().add(curr);
            }
        }
        List<String> resultList = stack.pop();
        Collections.sort(resultList, Collections.reverseOrder());
        StringBuilder result = new StringBuilder();
        for (String str : resultList) result.append(str);
        return result.toString();
    }
}
