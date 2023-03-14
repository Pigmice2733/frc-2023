// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.drivetrain.autoDrive.FollowPath;
import frc.robot.commands.drivetrain.balance.DriveOntoChargeStation;
import frc.robot.commands.drivetrain.balance.DriveOverChargeStation;
import frc.robot.subsystems.Drivetrain;

public class LeaveAndBalance extends SequentialCommandGroup {
  /**
   * A routine to be run in auto. Leaves the community, then
   * balances. Robot needs to start in line with cube scoring zone specified with
   * startLocation.
   */
  public LeaveAndBalance(Drivetrain drivetrain, TargetLocation startLocation) {
    // (1) Drive out of community and line up to balance
    if (startLocation == TargetLocation.Center) {
      addCommands(
          new DriveOntoChargeStation(drivetrain, true),
          new DriveOverChargeStation(drivetrain, true));
    }
    else if (startLocation == TargetLocation.Left) {
      addCommands(
          new FollowPath(drivetrain, "ScoreLeaveBalanceDriverLeft"));
    }
    else if (startLocation == TargetLocation.Right) {
      addCommands(
          new FollowPath(drivetrain, "ScoreLeaveBalanceDriverRight"));
    }

    // (2) Balance
    addCommands(new BalanceRoutine(drivetrain, false));
    addRequirements(drivetrain);
  }
}
