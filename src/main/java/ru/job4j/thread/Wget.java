package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    private final String fileName;

    public static long downloadSpeed;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesReads;
            int bytesWrited = 0;
            long deltaTime;
            long start = System.currentTimeMillis();
            while ((in.read()) != -1) {
                bytesReads = in.read(dataBuffer, 0, 1024);
                out.write(dataBuffer, 0, bytesReads);
                bytesWrited += bytesReads;
                if (bytesWrited >= speed) {
                    long end = System.currentTimeMillis();
                    deltaTime = (end - start);
                    if (deltaTime < 1000) {
                        System.out.println(deltaTime);
                        try {
                            Thread.sleep(1000 - deltaTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    start = System.currentTimeMillis();
                    bytesWrited = 0;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect parameters!!!"
                    + "\n 1 parameter: url (http://...)"
                    + "\n 2 parameter: limit speed downloads (b/s)"
                    + "\n 3 parameter: name new file (text)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        String fileName = args[2];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
