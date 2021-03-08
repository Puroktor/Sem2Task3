package ru.vsu.cs.skofenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOClass {
    public static int[][] readMatrixFromFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextLine());
        }
        int[][] mat = new int[list.size()][];
        int l = -1;
        for (int i = 0; i < list.size(); i++) {
            int[] temp = Arrays.stream(list.get(i).split(" ")).mapToInt(Integer::parseInt).toArray();
            if (temp.length != l) {
                if (l == -1)
                    l = temp.length;
                else
                    throw new InputMismatchException();
            }
            mat[i] = Arrays.stream(temp).toArray();
        }
        return mat;
    }

    public static void writeMatrixToFile(String file, int[][] outArray) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new File(file))) {
            int i=0;
            for (int[] arr : outArray) {
                for (int a : arr) {
                    pw.print(a + " ");
                }
                if (arr.length != 0 && i!= outArray.length-1 ) {
                    pw.println();
                }
                i++;
            }
        } catch (NullPointerException ignored) {
        }
    }
}
