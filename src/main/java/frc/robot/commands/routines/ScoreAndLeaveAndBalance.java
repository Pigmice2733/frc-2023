// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.drivetrain.autoDrive.FollowPath;
import frc.robot.commands.drivetrain.balance.DriveOntoChargeStation;
import frc.robot.commands.drivetrain.balance.DriveOverChargeStation;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreAndLeaveAndBalance extends SequentialCommandGroup {
  /**
   * A routine to be run in auto. Scores the object the robot starts with, leaves
   * the community, then
   * balances. Robot needs to start in line with cube scoring zone specified with
   * startLocation.
   */
  public ScoreAndLeaveAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw, TargetLocation startLocation) {
    // (1) Score
    addCommands(new ScoreObject(drivetrain, arm, claw, ArmHeight.High, false), arm.setSetpointCommand(ArmHeight.Floor));

    SequentialCommandGroup group = new SequentialCommandGroup();
    // (2) Drive out of community and line up to balance
    if (startLocation == TargetLocation.Center) {
      group.addCommands(
          new DriveOntoChargeStation(drivetrain, true),
          new DriveOverChargeStation(drivetrain, true));
    } else if (startLocation == TargetLocation.Left) {
      group.addCommands(
          new FollowPath(drivetrain, "ScoreLeaveBalanceDriverLeft", true));
    } else if (startLocation == TargetLocation.Right) {
      group.addCommands(
          new FollowPath(drivetrain, "ScoreLeaveBalanceDriverRight", true));
    }

    addCommands(Commands.parallel(arm.setSetpointCommand(ArmHeight.Floor), group));

    // (3) Balance
    addCommands(new BalanceRoutine(drivetrain, true));
    addRequirements(drivetrain, arm, claw);
  }
}
