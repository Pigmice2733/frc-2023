// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.automated.ScoreObject;
import frc.robot.commands.drivetrain.AutoBalanceWithRoll;
import frc.robot.commands.drivetrain.DriveOverChargeStation;
import frc.robot.commands.drivetrain.DriveOntoChargeStation;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreAndBalance extends SequentialCommandGroup {
  /**
   * A routine to be run in auto. Scores the object the robot starts with then
   * balances. Robot needs to start in line with charge station.
   */
  public ScoreAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    addCommands(
        new ScoreObject(drivetrain, arm, claw),
        new DriveOntoChargeStation(drivetrain, true),
        new DriveOverChargeStation(drivetrain, true),
        new DriveOntoChargeStation(drivetrain),
        new AutoBalanceWithRoll(drivetrain));
  }
}
