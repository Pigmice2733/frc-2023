package frc.robot.lights;

public class RGB {
    private final int[] rgb;

    public RGB(int r, int g, int b) {
        rgb = new int[] { r, g, b };
    }

    public RGB(int[] rgb) {
        this.rgb = rgb;
    }

    public int[] getRGB() {
        return this.rgb;
    }

    public int getR() {
        return this.rgb[0];
    }

    public int getG() {
        return this.rgb[1];
    }

    public int getB() {
        return this.rgb[2];
    }
}
