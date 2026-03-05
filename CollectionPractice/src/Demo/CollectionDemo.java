package Demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CollectionDemo {
    public static void main(String[] args) {
        Collection<String> collection = new ArrayList<>();

        collection.add("apple");
        System.out.println("add: "+ collection);

        collection.addAll(Arrays.asList("Banana","Cherry"));
        System.out.println("addAll: "+ collection);

        System.out.println("size: "+ collection.size());

        System.out.println("isEmpty: "+ collection.isEmpty());

        System.out.println("Contains apple: "+collection.contains("apple"));



    }
}
