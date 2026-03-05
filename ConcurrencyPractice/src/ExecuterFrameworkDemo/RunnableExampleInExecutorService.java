package ExecuterFrameworkDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExampleInExecutorService {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Task completed by " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Main thread continues working...");

        executor.shutdown();
    }
}
