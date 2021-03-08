package ru.vsu.cs.skofenko;

public class Answer {
    private final int width;
    private final int height;
    private final int leftI;
    private final int leftJ;

    public Answer(int width, int height, int leftX, int leftY) {
        this.width = width;
        this.height = height;
        this.leftI = leftX;
        this.leftJ = leftY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLeftI() {
        return leftI;
    }

    public int getLeftJ() {
        return leftJ;
    }
}
