package frc.robot.commands.drivetrain.defaultCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class HoldMotors extends CommandBase {
    private Drivetrain drivetrain;

    public HoldMotors(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        this.drivetrain.driveVoltages(0, 0);
    }
}
