package frc.robot.commands.lights;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Animation;
import frc.robot.subsystems.Lights;

public class AnimateLights extends CommandBase {
    private Lights lights;
    private Animation animation;

    public AnimateLights(Lights lights, Animation animation) {
        this.lights = lights;
        this.animation = animation;

        addRequirements(lights);
    }

    @Override
    public void execute() {
        animation.display(lights);
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
