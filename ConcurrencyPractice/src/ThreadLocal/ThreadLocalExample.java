package ThreadLocal;

public class ThreadLocalExample {

    static ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> (int)(Math.random() * 1000));

    public static void main(String[] args) {

        Runnable  task = () -> {
            System.out.println(
                    Thread.currentThread().getName() +
                            " ID: " + threadId.get()
            );
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
    }
}
