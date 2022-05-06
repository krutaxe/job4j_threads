package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public static long downloadSpeed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("10Mb_tmp.dat")) {
            byte[] dataBuffer = new byte[1024];
            int bytesReads;
            int bufferDownload = 0;
            long time;
            long start = System.currentTimeMillis();
            while ((in.read()) != -1) {
                bytesReads = in.read(dataBuffer, 0, 1024);
                out.write(dataBuffer, 0, bytesReads);
                bufferDownload += bytesReads;
            }
            long end = System.currentTimeMillis();
            time = end - start;
            downloadSpeed = (bufferDownload) / (time / 1000);
            if ((downloadSpeed) > (speed)) {
                long timeSleep = ((bufferDownload / speed) * 1000L) - time;
                if (timeSleep > 0) {
                    try {
                        Thread.sleep(timeSleep);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Incorrect parameters");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
