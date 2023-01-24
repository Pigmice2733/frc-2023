package frc.robot.lights;

import frc.robot.lights.Text.TextSequence;

public class Image {
    private int[][] buffer = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    public Image() {
    }

    public Image(int[][] buffer) {
        this.buffer = buffer;
    }

    public static Image from(Image other) {
        Image newImage = new Image();
        for (int y2 = 0; y2 < other.getBuffer().length; y2++) {
            for (int x2 = 0; x2 < other.getBuffer()[0].length; x2++) {
                newImage.getBuffer()[y2][x2] = other.getBuffer()[y2][x2];
            }
        }

        return newImage;
    }

    public int getPixel(int x, int y) {
        return buffer[y][x];
    }

    public void setPixel(int color, int x, int y) {
        if (x < 0 || y < 0 || x > buffer[0].length - 1 || y > buffer.length - 1)
            return;
        buffer[y][x] = color;
    }

    public void setBuffer(int[][] buffer) {
        this.buffer = buffer;
    }

    public int[][] getBuffer() {
        return this.buffer;
    }

    public void imposeImage(Image grid, int x, int y) {
        for (int i = 0; i < grid.getBuffer().length; i++) {
            for (int j = 0; j < grid.getBuffer()[0].length; j++) {
                this.setPixel(grid.getPixel(j, i), x + j, y + i);
            }
        }
    }

    public void imposeGrid(int[][] grid, int x, int y) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (x + j < 0 || y + i < 0 || x + j > buffer[0].length - 1 || y + i > buffer.length - 1)
                    continue;
                this.setPixel(grid[i][j], x + j, y + i);
            }
        }
    }

    public void imposeText(String text, int x, int y, int color) {
        TextSequence letters = Text.buildLetters(text);
        int xOffset = 0;
        for (int[][] letterImage : letters.getLetters()) {
            for (int y_2 = 0; y_2 < letterImage.length; y_2++) {
                for (int x_2 = 0; x_2 < letterImage[y_2].length; x_2++) {
                    if (x + x_2 + xOffset < 0 || x + x_2 + xOffset >= 16 || y + y_2 < 0 || y + y_2 > 15)
                        continue;
                    if (letterImage[y_2][x_2] > 0) {
                        this.setPixel(color, x + x_2 + xOffset, y + y_2);
                    }
                }
            }
            xOffset += letterImage[0].length + 1;
        }
    }
}
