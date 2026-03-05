package ExecuterFrameworkDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExample {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {

            int taskId = i;

            executor.submit(() -> {
                System.out.println(
                        "Task " + taskId +
                                " running on " +
                                Thread.currentThread().getName()
                );
            });
        }

        executor.shutdown();
    }
}