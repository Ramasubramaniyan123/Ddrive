package InterruptExample;

import java.util.concurrent.atomic.AtomicInteger;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger i= new AtomicInteger(0);
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(i.getAndIncrement() + " Thread is running");
            }
            System.out.println("Thread stopped");
        });
        thread.start();
        Thread.sleep(10);
        thread.interrupt();
    }
}
