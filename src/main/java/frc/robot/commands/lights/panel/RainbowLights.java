package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightsPanel;

public class RainbowLights extends CommandBase {
    private int HUE_OFFSET = 0;
    private int HUE_STEP = 3;

    private LightsPanel lights;

    public RainbowLights(LightsPanel lights) {
        this.lights = lights;

        addRequirements(lights);
    }

    @Override
    public void execute() {
        lights.displayRainbow(HUE_OFFSET, HUE_STEP);
        // lights.displayHeart();

        HUE_OFFSET += HUE_STEP;
        HUE_OFFSET %= 180;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("ENDING RAINBOW");
        this.lights.clearPanel();
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
