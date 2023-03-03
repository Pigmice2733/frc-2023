// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.automated.ScoreObject;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.commands.drivetrain.DriveOverChargeStation;
import frc.robot.commands.drivetrain.TurnDegreesPID;
import frc.robot.commands.rotatingArm.RotateArmToScoreHeight.ScoreHeight;
import frc.robot.commands.drivetrain.DriveOntoChargeStation;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreAndLeaveAndBalance extends SequentialCommandGroup {
  /**
   * A routine to be run in auto. Scores the object the robot starts with, leaves the community, then
   * balances. Robot needs to start in line with cube scoring zone specified with startLocation, which will default to Center.
   */
  public ScoreAndLeaveAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    this(drivetrain, arm, claw, TargetLocation.Center);
  }

  public ScoreAndLeaveAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw, TargetLocation startLocation) {
    if (startLocation == TargetLocation.Center) {
    addCommands(
       new ScoreObject(drivetrain, arm, claw, ScoreHeight.High, true),
        new DriveOntoChargeStation(drivetrain, true),
        new DriveOverChargeStation(drivetrain, true),
        new BalanceRoutine(drivetrain));
    }
    else {
      addCommands(
          new ScoreObject(drivetrain, arm, claw, ScoreHeight.High, true),
          new DriveDistancePID(drivetrain, -5.6).withTimeout(5),
          new TurnDegreesPID(drivetrain, 40 * ((startLocation == TargetLocation.Left) ? -1.0 : 1.0)).withTimeout(2),
          new BalanceRoutine(drivetrain));
      }
      addRequirements(drivetrain, arm, claw);
  }
}
