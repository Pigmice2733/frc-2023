package frc.robot.commands.rotatingArm;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.subsystems.RotatingArm;
import frc.robot.Constants.ClawConfig;

public class RotateArmToAngle extends ProfiledPIDCommand {

    /**
     * Rotate the claw arm to a particular angle.
     * @param targetAngle angle to rotate to above straight down in degrees
     * @param arm the rotating-arm subsystem
     */
    public RotateArmToAngle (double targetAngle, RotatingArm arm){
        super(
            new ProfiledPIDController(
                ClawConfig.kP,
                ClawConfig.kI,
                ClawConfig.kD,
                new Constraints(ClawConfig.maxAcceleration, ClawConfig.maxVelocity)),
            arm::getAngle,
            targetAngle,
            (output, setpoint) -> {arm.rotateClaw(output);},
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
