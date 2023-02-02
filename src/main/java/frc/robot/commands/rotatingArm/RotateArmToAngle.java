package frc.robot.commands.rotatingArm;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.subsystems.RotatingArm;
import frc.robot.Constants.RotatingArmConfig;

public class RotateArmToAngle extends ProfiledPIDCommand {

    /**
     * Rotate the claw arm to a particular angle.
     * @param targetAngle angle to rotate to above straight down in degrees
     * @param arm the rotating-arm subsystem
     */
    public RotateArmToAngle (double targetAngle, RotatingArm arm) {
        super(
            new ProfiledPIDController(
                RotatingArmConfig.kP,
                RotatingArmConfig.kI,
                RotatingArmConfig.kD,
                new Constraints(RotatingArmConfig.maxAcceleration, RotatingArmConfig.maxVelocity)),
            arm::getAngle,
            targetAngle,
            (output, setpoint) -> {arm.setClawSpeed(output);},
            arm
        );

        getController().setTolerance(0.5, 0.1);
        addRequirements(arm);
    }
    
    @Override
    public void initialize() { }
  
    @Override
    public boolean isFinished() {
      return getController().atSetpoint();
    }
}
