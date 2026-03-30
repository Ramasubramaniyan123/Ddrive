package Day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class GoodBinaryStrings {
    public static String largestMagical(String s){
        Stack<List<String>> stack = new Stack<>();
        stack.push(new ArrayList<>());

        for(char c: s.toCharArray()){
            if(c == '1'){
                stack.push(new ArrayList<>());
            }
            else{
                List<String> top = stack.pop();
                top.sort(Collections.reverseOrder());
                StringBuilder sb = new StringBuilder();
                for(String str: top) sb.append(str);
                String curr = "1" + sb.toString() + "0";
                stack.peek().add(curr);
            }
        }
        List<String> list = stack.pop();
        list.sort(Collections.reverseOrder());
        StringBuilder sb = new StringBuilder();
        for(String str : list) sb.append(str);
        return sb.toString();
    }
}
