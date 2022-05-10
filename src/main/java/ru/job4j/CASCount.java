package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int temp;
        int newValue;
        do {
            temp = count.get();
            newValue = temp + 1;
        } while (!count.compareAndSet(temp, newValue));

    }

    public int get() {
        return count.get();
    }

}
