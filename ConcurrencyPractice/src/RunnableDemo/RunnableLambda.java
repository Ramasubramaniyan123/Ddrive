package RunnableDemo;

public class RunnableLambda {
    public static void main(String[] args) {
        Runnable task = () -> System.out.println("The thread is running on: " + Thread.currentThread().getName());
        Thread thread = new Thread(task);
        thread.start();
    }
}

