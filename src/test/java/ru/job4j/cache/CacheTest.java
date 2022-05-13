package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {
    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
        cache.add(new Base(3, 1));
        cache.add(new Base(3, 1));
        assertEquals(3, cache.getMemory().size());
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
        cache.add(new Base(3, 1));
        cache.delete(new Base(2, 1));
        assertNull(cache.getMemory().get(2));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
        cache.add(new Base(3, 1));

        cache.update(new Base(2, 1));
        cache.update(new Base(2, 2));

        assertEquals(3, cache.getMemory().get(2).getVersion());
    }

}