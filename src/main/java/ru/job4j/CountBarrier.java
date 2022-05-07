package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            monitor.notifyAll();
            count++;
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            count();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(1);
        Thread first = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " " + Thread.currentThread().getState());
                },
                "first");

        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " " + Thread.currentThread().getState());
                },
                "second");

        Thread three = new Thread(
                () -> {
                    countBarrier.count();
                    System.out.println(Thread.currentThread().getName()
                            + " " + Thread.currentThread().getState());
                },
                "three");

        Thread four = new Thread(
                () -> {
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName()
                            + " " + Thread.currentThread().getState());
                },
                "four");

        first.start();
        second.start();

        Thread.sleep(500);


        System.out.println("поток 1 - " + first.getState());
        System.out.println("поток 2 - " + second.getState());
        System.out.println("поток 3 - " + three.getState());

        System.out.println("_____________");

        three.start();

        four.start();

        Thread.sleep(2000);

        System.out.println("_____________");

        System.out.println("поток 1 - " + first.getState());
        System.out.println("поток 2 - " + second.getState());
        System.out.println("поток 3 - " + three.getState());
        System.out.println("поток 4 - " + four.getState());
    }
}
