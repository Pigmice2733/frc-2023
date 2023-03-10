package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class HoldPosition extends CommandBase {
    private final Drivetrain drivetrain;
    private PIDController holdPID = new PIDController(0.6, 0, 0.0);

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public HoldPosition(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.holdPID.setTolerance(0.01, 0.05);

        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.holdPID.setSetpoint(this.drivetrain.getAverageDistance());
        System.out.println("INITIALIZING COMMAND WITH SETPOINT " + this.holdPID.getSetpoint());
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double speed = this.holdPID.calculate(this.drivetrain.getAverageDistance());
        SmartDashboard.putNumber("Hold Output", speed);
        SmartDashboard.putNumber("DIST", this.drivetrain.getAverageDistance());
        this.drivetrain.arcadeDrive(speed, 0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
