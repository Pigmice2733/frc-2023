// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class AutoBalance extends CommandBase {
  private Drivetrain drivetrain;
  private PIDController balancePID = new PIDController(0.028, 0, 0.0035);

  /** Robot must be lined up with the charge station. */
  public AutoBalance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    this.balancePID.setTolerance(0.1, 0.1);
    this.balancePID.setSetpoint(0);

    SmartDashboard.putData(balancePID);

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    // double pitch = MathUtil.applyDeadband(drivetrain.getPitch(), 1);

    // double proportional = pitch / 15.0;

    // double speed = proportional * DrivetrainConfig.autoBalanceProportional +
    // 0.075 * Math.signum(pitch); // was 0.1,
    // 0.07

    double speed = balancePID.calculate(drivetrain.getPitch());
    SmartDashboard.putNumber("balance/output", speed);

    speed = MathUtil.clamp(speed, -0.5, 0.5);

    drivetrain.arcadeDrive(speed, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
