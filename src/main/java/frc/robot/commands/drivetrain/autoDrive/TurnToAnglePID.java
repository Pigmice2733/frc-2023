// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.autoDrive;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class TurnToAnglePID extends ProfiledPIDCommand {

  private final double rotation;
  private final Drivetrain drivetrain;

  /**
   * Use profiled PID to rotate the specified angle.
   * 
   * @param drivetrain a drivetrain subsystem
   * @param rotation   The degrees to rotate
   */
  public TurnToAnglePID(Drivetrain drivetrain, double rotation) {
    super(
        new ProfiledPIDController(DrivetrainConfig.turnDegP, DrivetrainConfig.turnDegI, DrivetrainConfig.turnDegD,
            new Constraints(DrivetrainConfig.turnDegVel, DrivetrainConfig.turnDegAcc)),
        () -> drivetrain.getHeading().getDegrees(),
        rotation,
        (output, setpoint) -> {
          drivetrain.arcadeDrive(0, -output);
        },
        drivetrain);
    this.rotation = rotation;
    this.drivetrain = drivetrain;

    getController().enableContinuousInput(-180, 180);

    getController().setTolerance(2, 3);
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
