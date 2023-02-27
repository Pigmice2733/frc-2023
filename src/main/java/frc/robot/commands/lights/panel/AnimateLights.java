package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.lights.Animation;
import frc.robot.subsystems.LightsPanel;

public class AnimateLights extends InstantCommand {
    public AnimateLights(LightsPanel lights, Animation animation) {
        super(() -> lights.queueAnimation(animation));
        addRequirements(lights);
    }
}
