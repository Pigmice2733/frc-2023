// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.autoDrive;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class DriveDistancePID extends ProfiledPIDCommand {

  private final double distance;
  private final Drivetrain drivetrain;

  /**
   * Use profiled PID to drive the specified distance.
   * 
   * @param drivetrain a drivetrain subsystem
   * @param distance   The distance to drive in meters. The robot will move
   *                   forward if this is positive or backward if this is
   *                   negative.
   */
  public DriveDistancePID(Drivetrain drivetrain, double distance) {
    super(
        new ProfiledPIDController(DrivetrainConfig.driveDistP, DrivetrainConfig.driveDistI, DrivetrainConfig.driveDistD,
            new Constraints(DrivetrainConfig.driveDistVel, DrivetrainConfig.driveDistAcc)),
        drivetrain::getAverageDistance,
        100000,
        (output, setpoint) -> {
          drivetrain.arcadeDrive(output, 0);
        },
        drivetrain);
    this.distance = distance;
    this.drivetrain = drivetrain;

    getController().setTolerance(0.05, 0.1);
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    super.initialize();
    double targetDist = distance + drivetrain.getAverageDistance();
    m_goal = () -> new State(targetDist, 0);
    getController().setGoal(m_goal.get());
    SmartDashboard.putData(getController());
  }

  @Override
  public void execute() {
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
