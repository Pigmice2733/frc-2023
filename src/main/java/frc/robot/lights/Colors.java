package frc.robot.lights;

public class Colors {
    public static RGB[] COLORS = new RGB[16];
    public static byte BLACK = 0;
    public static byte WHITE = 1;
    public static byte YELLOW = 2;
    public static byte ORANGE = 3;
    public static byte RED = 4;
    public static byte MAGENTA = 5;
    public static byte PURPLE = 6;
    public static byte BLUE = 7;
    public static byte CYAN = 8;
    public static byte GREEN = 9;
    public static byte DARK_GREEN = 10;
    public static byte BROWN = 11;
    public static byte TAN = 12;
    public static byte LIGHT_GRAY = 13;
    public static byte MEDIUM_GRAY = 14;
    public static byte DARK_GRAY = 15;

    private static byte MAX_COLOR = 15;

    static {
        COLORS = new RGB[] {
                new RGB(0, 0, 0),
                new RGB(200, 200, 200),
                new RGB(255, 255, 0),
                new RGB(255, 165, 0),
                new RGB(255, 0, 0),
                new RGB(255, 0, 255),
                new RGB(128, 0, 128),
                new RGB(0, 0, 255),
                new RGB(0, 200, 255),
                new RGB(0, 230, 0),
                new RGB(34, 139, 34),
                new RGB(128, 0, 0),
                new RGB(210, 180, 140),
                new RGB(180, 180, 180),
                new RGB(128, 128, 128),
                new RGB(47, 79, 79)
        };

        // int numColors = 14;
        // for (int i = 0; i < numColors; i++) {
        // COLORS[i + 2] = new RGB(Color.fromHSV((int) Math.floor((i * 180.0) /
        // numColors), (int) (255 * 0.8), 50));
        // }
    }
}
