package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class SimpleBlockingQueueTest {

    @Test
    public void queue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sq = new SimpleBlockingQueue<>(5);
        List<Integer> result = new ArrayList<>();
        List<Integer> expected = List.of(3, 2, 1);
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sq.offer(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sq.offer(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sq.offer(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result.add(sq.poll());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    result.add(sq.poll());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    result.add(sq.poll());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(expected, result);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach( e -> {
                        try {
                            queue.offer(e);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenFetchAllThenGetItAnalog() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        double a = Math.random() * 100;
                        try {
                            queue.offer((int) a);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        producer.start();

        Thread consumer = new Thread(
                () -> {
                    while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer.size(), is(10));
    }

}