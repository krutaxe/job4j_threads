package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static ru.job4j.pool.RolColSum.*;

public class RolColSumTest {
    @Test
    public void whenSync() {
        int[][] array = {{1, 2, 3},
                         {4, 5, 6},
                         {7, 8, 9}};

        RolColSum.Sums[] expected = {new RolColSum.Sums(6, 12),
                                     new RolColSum.Sums(15, 15),
                                     new RolColSum.Sums(24, 18)};
        assertEquals(expected, sum(array));
    }

    @Test
    public void whenAsync() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 2, 3},
                         {4, 5, 6},
                         {7, 8, 9}};

        RolColSum.Sums[] expected = {new RolColSum.Sums(6, 12),
                                     new RolColSum.Sums(15, 15),
                                     new RolColSum.Sums(24, 18)};

        assertEquals(expected, asyncSum(array));
    }
}