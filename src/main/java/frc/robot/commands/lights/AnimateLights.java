package frc.robot.commands.lights;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.lights.Animation;
import frc.robot.subsystems.Lights;

public class AnimateLights extends InstantCommand {
    public AnimateLights(Lights lights, Animation animation) {
        super(() -> lights.queueAnimation(animation));
        addRequirements(lights);
    }
}
