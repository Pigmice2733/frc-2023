package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Image;
import frc.robot.subsystems.LightsPanel;

public class DisplayTestColors extends CommandBase {
    private LightsPanel lights;

    public DisplayTestColors(LightsPanel lights) {
        this.lights = lights;
        Image image = new Image();
        for (byte i = 0; i < 16; i++)
            for (byte y = 0; y < 16; y++)
                image.setPixel(i, i, y);
        lights.displayImage(image);
    }
}
