package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

}