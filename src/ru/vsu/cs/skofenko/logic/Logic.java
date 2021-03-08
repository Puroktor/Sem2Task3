package ru.vsu.cs.skofenko.logic;

import ru.vsu.cs.skofenko.Answer;

import java.util.Arrays;

public class Logic {
    private static class ArrRotator {
        @FunctionalInterface
        private interface GetFunction {
            int function(int index);
        }

        private final int[] arr;
        private final int length;
        private final GetFunction getIndexFunction;

        public ArrRotator(int[] arr, boolean isStraight) {
            this.arr = arr;
            length = arr.length;
            if (isStraight) {
                getIndexFunction = this::getStraight;
            } else {
                getIndexFunction = this::getReverse;
            }
        }

        public int getIndex(int index) {
            return getIndexFunction.function(index);
        }

        public int getElement(int index) {
            return arr[getIndex(index)];
        }

        private int getStraight(int index) {
            return index;
        }

        private int getReverse(int index) {
            return length - index - 1;
        }
    }

    private static IStack<Integer> stack;

    public static Answer solution(int[][] mat, boolean useStandardStack) {
        if (mat.length == 0) {
            return new Answer(0, 0, 0, 0);
        }
        int n = mat.length, m = mat[0].length;
        int leftI = 0, leftJ = 0, width = 0, height = 0;
        int ans = 0;
        int[] d = new int[m];//верхняя гранциа
        Arrays.fill(d, -1);
        int[] leftB;//левая граница
        int[] rightB;//правая граница
        initStack(useStandardStack);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 0) {
                    d[j] = i;
                }
            }
            leftB = sequentialAlgorithm(new ArrRotator(d, true));
            rightB = sequentialAlgorithm(new ArrRotator(d, false));
            for (int j = 0; j < m; j++) {
                int s = (i - d[j]) * (rightB[j] - leftB[j] - 1);
                if (s > ans) {
                    ans = s;
                    leftI = d[j] + 1;
                    leftJ = j;
                    width = rightB[j] - leftB[j] - 1;
                    height = i - d[j];
                }
            }
        }
        return new Answer(width, height, leftI, leftJ);
    }// по времени O(n * m), по памяти O(m)

    private static int[] sequentialAlgorithm(ArrRotator arr) {
        int[] d1 = new int[arr.length];
        for (int j = 0; j < arr.length; j++) {
            while (!stack.empty() && arr.getElement(stack.peek()) <= arr.getElement(j)) {
                stack.pop();
            }
            d1[arr.getIndex(j)] = stack.empty() ? arr.getIndex(-1) : arr.getIndex(stack.peek());
            stack.push(j);
        }
        stack.clear();
        return d1;
    }//O(m) по времени, O(m) по памяти

    private static void initStack(boolean useStandardStack) {
        if (useStandardStack) {
            stack = new StandardStack<>(); // просто обертка Stack<>(), чтобы не было повторений
        } else {
            stack = new MyStack<>();
        }
    }
}