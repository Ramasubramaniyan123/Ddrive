package ProducerConsumer;

public class Buffer {

    private int item;
    private boolean available = false;

    public synchronized void produce(int value) throws InterruptedException {

        while (available) {
            wait();   // Wait if buffer is full
        }

        item = value;
        available = true;

        System.out.println("Produced: " + value);

        notifyAll(); // Wake up waiting consumers
    }

    public synchronized int consume() throws InterruptedException {

        while (!available) {
            wait();   // Wait if buffer is empty
        }

        int value = item;
        available = false;

        System.out.println("Consumed: " + value);

        notifyAll(); // Wake up producer

        return value;
    }
}

