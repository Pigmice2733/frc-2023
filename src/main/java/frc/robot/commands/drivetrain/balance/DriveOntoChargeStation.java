// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ShuffleboardConfig;
import frc.robot.subsystems.Drivetrain;

public class DriveOntoChargeStation extends CommandBase {
  private final Drivetrain drivetrain;
  private boolean backwards = false;
  private double onStationTimestamp = -1;
  private static final double EXTRA_DRIVE_SECONDS = 1.5;
  private double initialPosition = -1;
  private double speedMultiplier = 2.0;
  private boolean onChargeStation = false;

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
    onStationTimestamp = -1;
    speedMultiplier = 2.0;
    if (ShuffleboardConfig.debugPrintsEnabled)
      SmartDashboard.putBoolean("OnStation", false);
    onChargeStation = false;

  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive((backwards ? -1 : 1) * speedMultiplier, 0);

    // store timestamp of getting on charge station
    if (!onChargeStation && Math.abs(drivetrain.getPitch()) > 12) {
      onChargeStation = true;
      onStationTimestamp = Timer.getFPGATimestamp();
      speedMultiplier = 1.0;
      initialPosition = drivetrain.getAverageDistance();
      if (ShuffleboardConfig.debugPrintsEnabled) {
        SmartDashboard.putBoolean("OnStation", true);
        // timer.reset();
        // SmartDashboard.putNumber("On Station Time", onStationTimestamp);
        SmartDashboard.putNumber("Initial Position", initialPosition);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    // onStationTimestamp != -1 when angle has been > 12
    // only ends after EXTRA_DRIVE_SECONDS have elapsed since getting on charge
    // station
    // return onChargeStation && onStationTimestamp != -1 &&
    // (Timer.getFPGATimestamp() -
    // onStationTimestamp) > EXTRA_DRIVE_SECONDS;
    SmartDashboard.putNumber("Current Position", drivetrain.getAverageDistance());
    return onChargeStation && initialPosition != -1
        && Math.abs(drivetrain.getAverageDistance() - initialPosition) >= 0.7;
    // return onStationTimestamp != -1 && timer.hasElapsed(EXTRA_DRIVE_SECONDS);
  }
}
