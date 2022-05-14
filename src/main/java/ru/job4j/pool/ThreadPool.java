package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool(SimpleBlockingQueue<Runnable> tasks) throws InterruptedException {
        for (int i = 1; i <= size; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (!tasks.isEmpty()) {
                            work(tasks.poll());
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
            threads.get(i - 1).start();
        }
        shutdown();
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

        for (int i = 1; i <= size; i++) {
            tasks.offer(new Runnable() {
                @Override
                public void run() {
                    int rsl = 0;
                    for (int i = 0; i < 1000000; i++) {
                        rsl += i;
                    }
                }
            });
        }

        new ThreadPool(tasks);
    }
}
