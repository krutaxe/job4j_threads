package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        System.out.println(queue.poll());
                    }
                }
        );
        consumer.start();

       Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

       producer.start();
       producer.join();

       consumer.interrupt();

    }
}
