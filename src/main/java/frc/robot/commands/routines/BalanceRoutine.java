// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.AutoBalanceWithRoll;
import frc.robot.commands.drivetrain.DriveOntoChargeStation;
import frc.robot.subsystems.Drivetrain;

public class BalanceRoutine extends SequentialCommandGroup {
  /** Drives the robot onto the Charge Station and balances. */
  public BalanceRoutine(Drivetrain drivetrain) {
    addCommands(
        new DriveOntoChargeStation(drivetrain, true),
        new AutoBalanceWithRoll(drivetrain, true));
  }
}
