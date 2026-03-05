package ExecuterFrameworkDemo;

import java.util.concurrent.*;

public class CallableExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(1000);
            return 42;
        });

        System.out.println("Doing other work...");

        Integer result = future.get();

        System.out.println("Result: " + result);

        executor.shutdown();
    }
}