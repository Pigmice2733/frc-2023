package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RumbleController extends SequentialCommandGroup {
    private XboxController controller;

    public RumbleController(XboxController controller, RumbleType type) {
        this(controller, type, 1.0, 0.5);
    }

    public RumbleController(XboxController controller, RumbleType type, double strength) {
        this(controller, type, 1.0, strength);
    }

    public RumbleController(XboxController controller, RumbleType type, double seconds, double strength) {
        this.controller = controller;

        addCommands(
                new InstantCommand(() -> this.controller.setRumble(type, strength)),
                new WaitCommand(seconds),
                new InstantCommand(() -> this.controller.setRumble(type, 0)));
    }
}
