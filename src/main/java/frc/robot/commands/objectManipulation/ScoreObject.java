// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.objectManipulation;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.autoDrive.DriveDistancePID;
import frc.robot.commands.drivetrain.autoDrive.FollowPath;
import frc.robot.commands.rotatingArm.RotateArmToAngle;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreObject extends SequentialCommandGroup {
  /**
   * Automatically scores a held object at the currently set score height. Does
   * not auto align.
   */
  public ScoreObject(Drivetrain drivetrain, RotatingArm arm, Claw claw, boolean lowerArm) {
    this(drivetrain, arm, claw, RotateArmToAngle.getScoreHeight(), lowerArm);
  }

  /**
   * Automatically scores a held object. Does not auto align.
   */
  public ScoreObject(Drivetrain drivetrain, RotatingArm arm, Claw claw, ArmHeight height, boolean lowerArm) {
        addCommands(new RotateArmToAngle(arm, height).withTimeout(2));

    addCommands(
      new FollowPath(drivetrain, "DriveAgainstGrid", false),
      claw.openClawCommand(true));
      
    if (lowerArm) {
      addCommands(
        new FollowPath(drivetrain, "DriveAgainstGrid", true),
          new RotateArmToAngle(arm, ArmHeight.Floor));
    }

    addRequirements(drivetrain, arm, claw);
  }
}
