// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.balance.AutoBalance;
import frc.robot.commands.drivetrain.balance.AutoBalanceNew;
import frc.robot.commands.drivetrain.balance.AutoBalanceProfiled;
import frc.robot.commands.drivetrain.balance.DriveOntoChargeStation;
import frc.robot.subsystems.Drivetrain;

public class BalanceRoutine extends SequentialCommandGroup {
  /** Drives the robot onto the Charge Station and balances. */
  public BalanceRoutine(Drivetrain drivetrain) {
    this(drivetrain, false);
  }

  public BalanceRoutine(Drivetrain drivetrain, boolean backwards) {
    addCommands(
        new DriveOntoChargeStation(drivetrain, backwards),
        new AutoBalance(drivetrain));
  }
}
