package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void queue() throws InterruptedException {
        SimpleBlockingQueue<Integer> sq = new SimpleBlockingQueue<>();
        List<Integer> result = new ArrayList<>();
        List<Integer> expected = List.of(3, 2, 1);
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                sq.offer(3);
                sq.offer(2);
                sq.offer(1);
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
              result.add(sq.poll());
              result.add(sq.poll());
              result.add(sq.poll());
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(expected, result);
    }

}