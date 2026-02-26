package RunnableDemo;

public class RunnableBasic {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyTask());
        thread.start();
    }

}
class MyTask implements Runnable{
    @Override
    public void run() {
        System.out.println("The task is running on: "+Thread.currentThread().getName());
    }
}
