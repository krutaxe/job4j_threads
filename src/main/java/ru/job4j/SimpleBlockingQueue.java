package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int limit = 5;
    private final Object lock = new Object();

    public void offer(T value) {
        synchronized (lock) {
            while (queue.size() == limit) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.offer(value);
            lock.notify();
        }
    }

    public T poll() {
        T result;
        synchronized (lock) {
            while (queue.size() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            result = queue.poll();
            lock.notify();
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();


        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 11; i++) {
                    simpleBlockingQueue.offer(i);
                }
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    simpleBlockingQueue.poll();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
