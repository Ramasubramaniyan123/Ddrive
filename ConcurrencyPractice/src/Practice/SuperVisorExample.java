package Practice;

public class SuperVisorExample {
    public static void main(String[] args) {

        try {
            new Worker1().work();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Worker2().work();
    }
}
class Worker1{
    public void work() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println("Worker 1 is working : "+ i);
        }
    }
}
class Worker2{
    public void work()  {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Worker 2 is working : "+ i);
        }
    }
}
