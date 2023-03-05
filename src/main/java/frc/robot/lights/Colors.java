package frc.robot.lights;

import edu.wpi.first.wpilibj.util.Color;

public class Colors {
    public static RGB[] COLORS = new RGB[16];

    static {
        COLORS[0] = new RGB(0, 0, 0);
        COLORS[1] = new RGB(255, 255, 255);

        int numColors = 14;
        for (int i = 0; i < numColors; i++) {
            COLORS[i + 2] = new RGB(Color.fromHSV((i * 360) / numColors, 100, 50));
        }
    }
}
