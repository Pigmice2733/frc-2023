// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveOntoChargeStation extends CommandBase {
  private final Drivetrain drivetrain;
  private boolean backwards = false;

  public DriveOntoChargeStation(Drivetrain drivetrain, boolean backwards) {
    this.drivetrain = drivetrain;
    this.backwards = backwards;
    addRequirements(drivetrain);
  }

  public DriveOntoChargeStation(Drivetrain drivetrain) {
    this(drivetrain, false);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive((backwards ? -1 : 1) * 1.0, 0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getPitch()) > 10;
  }
}
