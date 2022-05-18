package ru.job4j.pool;

import org.junit.Test;
import static org.junit.Assert.*;

public class ForkJoinTest {

    @Test
    public void whenSearch10() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i + (4 * i);
        }
        int rsl = ForkJoin.findIndexElement(array, 250);
        assertEquals(50, rsl);
    }

    @Test
    public void whenSearch3() {
        Integer[] array = {3, 6, 7, 10, 1, 2, 4, 6, 99, 33, 55, 66, 11,
                6, 7, 10, 1, 2, 4, 6, 99, 33, 55, 66, 11, 6, 7, 10, 1, 2, 4, 6, 99, 33, 55, 66, 11};
        int rsl = ForkJoin.findIndexElement(array, 3);
        assertEquals(0, rsl);
    }

    @Test
    public void whenSearchW() {
        String[] array = {"A", "C", "F", "W", "X", "Y", "P", "R", "B",
                "A", "C", "F", "O", "X", "Y", "P", "R", "B", "L", "H", "U"};
        int rsl = ForkJoin.findIndexElement(array, "W");
        assertEquals(3, rsl);
    }

    @Test
    public void whenSearchChar_() {
        Character[] array = {'5', 'A', '-', '_', '4', 'q', 'f', '0', 'v',
                '5', 'A', '-', '0', '4', 'q', 'f', '0', 'v', '5', 'A', '-',
                ')', '4', 'q', 'f', '0', 'v'};
        int rsl = ForkJoin.findIndexElement(array, '_');
        assertEquals(3, rsl);
    }

    @Test
    public void whenSearchNotElement() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = i + (4 * i);
        }
        int rsl = ForkJoin.findIndexElement(array, 251);
        assertEquals(-1, rsl);
    }

}