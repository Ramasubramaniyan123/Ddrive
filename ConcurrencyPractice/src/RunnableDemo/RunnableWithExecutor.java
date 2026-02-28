package RunnableDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableWithExecutor {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Runnable runnable = () ->
            System.out.println("Running thread name: "+ Thread.currentThread().getName());
        service.submit(runnable);
        service.shutdown();
    }
}
