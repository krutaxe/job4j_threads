package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int left;
    private final int right;
    private final T element;

    public ForkJoin(T[] array, int left, int right, T element) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.element = element;
    }

    private int findIndex() {
        int rsl = -1;
        for (int i = left; i < right; i++) {
            if (array[i] == element) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> Integer findIndexElement(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ForkJoin<>(array, 0, array.length, element));
    }

    @Override
    protected Integer compute() {
        if (right - left <= 10) {
            return findIndex();
        }
        int mid = (left + right) / 2;
        ForkJoin<T> leftArray = new ForkJoin<>(array, left, mid, element);
        ForkJoin<T> rightArray = new ForkJoin<>(array, mid, right, element);
        leftArray.fork();
        rightArray.fork();
        return Math.max(leftArray.join(), rightArray.join());
    }
}
