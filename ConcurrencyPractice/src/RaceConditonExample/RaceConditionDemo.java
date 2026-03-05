package RaceConditonExample;

// RaceConditonExample.RaceConditionDemo.java — Run this multiple times to see different results!
public class RaceConditionDemo {
    private int count = 0;

    public void increment() {
        count++; // Not atomic! (Read -> Add -> Write)
    }

    public int getCount() { return count; }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionDemo counter = new RaceConditionDemo();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join(); // Wait for t1 to finish
        t2.join(); // Wait for t2 to finish

        System.out.println("Expected: 2000000");
        System.out.println("Actual:   " + counter.getCount());
    }
}