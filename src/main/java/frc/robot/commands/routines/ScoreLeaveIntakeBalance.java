// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.autoDrive.FollowPath;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreLeaveIntakeBalance extends SequentialCommandGroup {
  /**
   * A routine to be run in auto. Scores the object the robot starts with, leaves
   * the community, then
   * balances. Robot needs to start in line with cube scoring zone on DRIVER LEFT.
   */
  public ScoreLeaveIntakeBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    HashMap<String, Command> eventMap = new HashMap<>();
    eventMap.put("closeClaw", new ParallelCommandGroup(claw.closeClawCommand(true), arm.setSetpointCommand(ArmHeight.Floor)));

    addCommands(
      new ScoreObject(drivetrain, arm, claw, ArmHeight.High, false),
      arm.setSetpointCommand(ArmHeight.Zero),
      new FollowPath(drivetrain, "ScoreLeaveIntakeBalanceDriverLeft", eventMap, true),
      new BalanceRoutine(drivetrain, true));

    
    addRequirements(drivetrain, arm, claw);
  }
}
