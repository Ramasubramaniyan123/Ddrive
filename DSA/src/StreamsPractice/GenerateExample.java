package StreamsPractice;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GenerateExample {
    public static void main(String[] args) {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "My name is Ram";
            }
        };
        Consumer<String> consumer = new Consumer<String>() {
            int i = 0;
            @Override
            public void accept(String string) {
                System.out.println( i++ + "Hello: " + string);
            }
        };
        Stream<String> stream = Stream.generate(supplier);
        stream.forEach(consumer);
    }
}
