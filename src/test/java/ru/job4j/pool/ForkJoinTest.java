package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForkJoinTest {

    @Test
    public void whenSearch10() {
        Integer[] array = {3, 6, 7, 10};
        int rsl = ForkJoin.findIndexElement(array, 10);
        assertEquals(3, rsl);
    }

    @Test
    public void whenSearch3() {
        Integer[] array = {3, 6, 7, 10};
        int rsl = ForkJoin.findIndexElement(array, 3);
        assertEquals(0, rsl);
    }

    @Test
    public void whenSearchW() {
        String[] array = {"A", "C", "F", "W"};
        int rsl = ForkJoin.findIndexElement(array, "W");
        assertEquals(3, rsl);
    }

    @Test
    public void whenSearchChar_() {
        Character[] array = {'5', 'A', '-', '_'};
        int rsl = ForkJoin.findIndexElement(array, '_');
        assertEquals(3, rsl);
    }

}