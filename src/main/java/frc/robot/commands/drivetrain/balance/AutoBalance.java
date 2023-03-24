// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.ShuffleboardConfig;
import frc.robot.subsystems.Drivetrain;

public class AutoBalance extends CommandBase {
  private Drivetrain drivetrain;
  private PIDController balancePID = new PIDController(0.037, 0, 0.006);

  /** Robot must be lined up with the charge station. */
  public AutoBalance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    this.balancePID.setTolerance(3, 0.1);
    this.balancePID.setSetpoint(0);

    SmartDashboard.putData("balance/pid", balancePID);
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    if (ShuffleboardConfig.debugPrintsEnabled)
      SmartDashboard.putBoolean("Balance Running", true);
  }

  @Override
  public void execute() {
    double speed = balancePID.calculate(drivetrain.getPitch());
    SmartDashboard.putNumber("balance/output", speed);

    speed = MathUtil.clamp(speed, -6.0, 6.0);

    drivetrain
        .arcadeDrive(speed, 0);
  }

  @Override
  public void end(boolean canceled) {
    if (ShuffleboardConfig.debugPrintsEnabled)
      SmartDashboard.putBoolean("Balance Running", false);
  }

  @Override
  public boolean isFinished() {
    return balancePID.atSetpoint();
  }
}
