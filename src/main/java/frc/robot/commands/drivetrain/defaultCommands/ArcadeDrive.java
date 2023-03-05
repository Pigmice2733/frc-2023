package frc.robot.commands.drivetrain.defaultCommands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    private Drivetrain drivetrain;
    private DoubleSupplier forward, rotate;

    /**
     * Drive according to an arcade-style driving system.
     * @param drivetrain a drivetrain subsystem
     * @param forward the speed to move forward at
     * @param rotate the rotational speed to rotate at
     */
    public ArcadeDrive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier rotate) {
        this.drivetrain = drivetrain;
        this.forward = forward;
        this.rotate = rotate;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(forward.getAsDouble(), rotate.getAsDouble());
    }
}
