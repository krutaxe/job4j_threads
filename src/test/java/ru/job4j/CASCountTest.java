package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    @Test
    public void count20() throws InterruptedException {
        CASCount cas = new CASCount();

        Thread first = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    cas.increment();
                }
            }
        });

        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    cas.increment();
                }
            }
        });

        first.start();
        second.start();

        first.join();
        second.join();

        assertThat(20, is(cas.get()));
    }

}