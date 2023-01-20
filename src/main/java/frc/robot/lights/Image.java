package frc.robot.lights;

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
}
