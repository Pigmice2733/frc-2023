package frc.robot.lights;

import edu.wpi.first.wpilibj.util.Color;

public class RGB {
    private final int[] rgb;

    public RGB(int r, int g, int b) {
        rgb = new int[] { r, g, b };
    }

    public RGB(int[] rgb) {
        this.rgb = rgb;
    }

    public RGB(Color color) {
        this((int) (color.red * 255), (int) (color.green * 255), (int) (color.blue * 255));
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
