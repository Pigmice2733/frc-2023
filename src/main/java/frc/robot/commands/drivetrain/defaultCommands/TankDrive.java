package frc.robot.commands.drivetrain.defaultCommands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends CommandBase {
    private final Drivetrain drivetrain;
    private final DoubleSupplier left, right;

    /**
     * Drive according to an tank-drive system.
     * 
     * @param drivetrain a drivetrain subsystem
     * @param left       the speed to move the left wheels at
     * @param right      the speed to move the right wheels at
     */
    public TankDrive(Drivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
        this.drivetrain = drivetrain;
        this.left = left;
        this.right = right;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        //drivetrain.tankDrive(left.getAsDouble(), right.getAsDouble());
    }
}
