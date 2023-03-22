package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RumbleController extends WaitCommand {
    private XboxController controller;
    private double strength;

    public RumbleController(XboxController controller) {
        this(controller, 0.5, 0.5);
    }

    public RumbleController(XboxController controller, double strength) {
        this(controller, 0.5, strength);
    }

    public RumbleController(XboxController controller, double seconds, double strength) {
        super(seconds);
        this.controller = controller;
    }

    @Override
    public void initialize() {
        this.controller.setRumble(RumbleType.kBothRumble, strength);
    }

    @Override
    public void end(boolean interrupt) {
        this.controller.setRumble(RumbleType.kBothRumble, 0.0);
    }
}
