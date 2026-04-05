package Second10;

public class Question5 {
    public static void main(String[] args) {
        System.out.println(redSegment("WRRRWWR"));
        System.out.println(redSegment("RRRR"));
    }
    public static int redSegment(String string){
        int first = string.indexOf('R');
        int last = string.lastIndexOf('R');

        int count = 0;
         for(int i = first; i <= last ;i ++ ){
             if(string.charAt(i) == 'W') count++;
         }
        return count;
    }
}
