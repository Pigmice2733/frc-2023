// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.autoDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class DriveDistanceConstant extends CommandBase {
  private final Drivetrain drivetrain;
  private final double targetDistance;
  private boolean reverse;

  public DriveDistanceConstant(Drivetrain drivetrain, double distance) {
    this.drivetrain = drivetrain;
    this.targetDistance = distance + drivetrain.getAverageDistance();

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    reverse = (drivetrain.getAverageDistance() > targetDistance) ? true : false;
    System.out.println(reverse);
    SmartDashboard.putBoolean("Drive Command Running", true);
  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive(DrivetrainConfig.constantDriveDistSpeed * (reverse ? -1 : 1), 0);
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Drive Command Running", false);
    System.out.println("Drive Command done");
  }

  @Override
  public boolean isFinished() {
    if (reverse)
      return drivetrain.getAverageDistance() < targetDistance;
    else 
      return drivetrain.getAverageDistance() > targetDistance;
  }
}
