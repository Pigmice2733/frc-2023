package frc.robot.commands.lights;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lights;

public class RainbowLights extends CommandBase {
    private int HUE_OFFSET = 0;
    private int HUE_STEP = 3;

    private Lights lights;

    public RainbowLights(Lights lights) {
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
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
