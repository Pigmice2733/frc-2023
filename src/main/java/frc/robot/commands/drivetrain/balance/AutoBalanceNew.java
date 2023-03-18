// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoBalanceNew extends CommandBase {
  private boolean hasTipped = false;
  private final Drivetrain drivetrain;
  public AutoBalanceNew(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (!hasTipped) {
      drivetrain.arcadeDrive(0.35, 0);
      hasTipped = Math.abs(drivetrain.getPitch()) < 10;
    }
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return hasTipped;
  }
}
