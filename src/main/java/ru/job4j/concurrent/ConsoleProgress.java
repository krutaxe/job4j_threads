package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = new String[] {"--", "\\", "|", "/" };
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String s : process) {
                    Thread.sleep(250);
                    System.out.print("\r load: " + s);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
