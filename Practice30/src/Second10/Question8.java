package Second10;

import java.util.HashSet;
import java.util.Set;

public class Question8 {
    public static void main(String[] args) {
        System.out.println(Question8.countUniqueValidRooms(new int[]{1,3,3,7,2,1},5));
        System.out.println(Question8.countUniqueValidRooms(new int[]{6,7,8},5));
    }
    static int countUniqueValidRooms(int[] rooms, int k){
        Set<Integer> set = new HashSet<>();
        int count = 0;
        for(int r: rooms){
            if(1<=r && r <= k){
                if(set.add(r)) count++;
            }
        }
        return  count;
    }
}
