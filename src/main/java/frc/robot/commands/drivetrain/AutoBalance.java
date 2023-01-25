// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

/** Robot must be lined up with the charge station */
public class AutoBalance extends CommandBase {
  Drivetrain drivetrain;

  public AutoBalance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    double pitch = MathUtil.applyDeadband(drivetrain.getPitch(), 5);

    double proportional = pitch/15.0;

    double speed = proportional * DrivetrainConfig.autoBalanceProportional + 0.03*Math.signum(pitch); // was 0.1, 0.07
    speed = MathUtil.clamp(speed, -0.5, 0.5);

    drivetrain.arcadeDrive(speed, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
