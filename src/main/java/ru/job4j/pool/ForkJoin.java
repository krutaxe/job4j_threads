package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin extends RecursiveTask<Integer> {
    private final int[] array;
    private final int left;
    private final int right;
    private final int element;

    public ForkJoin(int[] array, int left, int right, int element) {
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
            }
        }
        return rsl;
    }

    public static int findIndexElement(int[] array, int element) {
        ForkJoin task = new ForkJoin(array, 0, array.length, element);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(task);
    }

    @Override
    protected Integer compute() {
        if (right - left <= 10) {
            return findIndex();
        } else {
            int mid = (left + right) / 2;
            RecursiveTask<Integer> leftArray = new ForkJoin(array, left, mid, element);
            RecursiveTask<Integer> rightArray = new ForkJoin(array, mid, right, element);
            leftArray.fork();
            rightArray.fork();
            return Math.max(leftArray.join(), rightArray.join());
        }
    }

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 3;
        }
        System.out.println(findIndexElement(array, 12));
    }
}
