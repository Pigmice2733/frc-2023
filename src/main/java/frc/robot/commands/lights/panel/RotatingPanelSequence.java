package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.lights.Images;
import frc.robot.subsystems.LightsPanel;

public class RotatingPanelSequence extends SequentialCommandGroup {
    private LightsPanel lights;
    private final int TIMEOUT = 10;

    public RotatingPanelSequence(LightsPanel lights) {
        this.lights = lights;
        addCommands(
                new ScrollSponsors(lights).withTimeout(TIMEOUT),
                new CGOLLights(lights).withTimeout(TIMEOUT),
                new ShowImage(lights, Images.PIGMICE).withTimeout(TIMEOUT),
                new RainbowLights(lights).withTimeout(TIMEOUT),
                // new SnakeGame(lights).withTimeout(TIMEOUT),
                new ShowImage(lights, Images.IZZY_PIGMICE).withTimeout(TIMEOUT));
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
