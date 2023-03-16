package frc.robot.lights;

import edu.wpi.first.wpilibj.util.Color;

public class Colors {
    public static RGB[] COLORS = new RGB[16];
    public static byte BLACK = 0;
    public static byte WHITE = 1;
    public static byte RED = 2;
    public static byte ORANGE = 3;
    public static byte LIGHT_ORANGE = 4;
    public static byte YELLOW = 5;
    public static byte LIME = 6;
    public static byte GREEN = 7;
    public static byte LIGHT_GREEN = 8;
    public static byte SKY_BLUE = 9;
    public static byte LIGHT_BLUE = 10;
    public static byte BLUE = 11;
    public static byte INDIGO = 12;
    public static byte PURPLE = 13;
    public static byte PINK = 14;
    public static byte MAGENTA = 15;

    static {
        COLORS[0] = new RGB(0, 0, 0);
        COLORS[1] = new RGB(255, 255, 255);

        int numColors = 14;
        for (int i = 0; i < numColors; i++) {
            COLORS[i + 2] = new RGB(Color.fromHSV((int) Math.floor((i * 180.0) / numColors), 255, 50));
        }
    }
}
