// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.autoDrive.DriveDistancePID;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreAndLeave extends SequentialCommandGroup {
  /** Score and leave from Left or Right. Start facing the grid, command does not turn robot around */
  public ScoreAndLeave(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    addCommands(
      new ScoreObject(drivetrain, arm, claw, ArmHeight.High, true, true),
      new DriveDistancePID(drivetrain, -3));
  }
}
