// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.commands.rotatingArm.RotateArmToAngleConstant;
import frc.robot.commands.rotatingArm.RotateArmToScoreHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreObject extends SequentialCommandGroup {
  /** Automatically scores a held object at the currently set score height and location. Does not auto align. */
  public ScoreObject(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    
    addCommands(
      new DriveDistancePID(drivetrain, -0.5),
      new RotateArmToScoreHeight(arm),
      new DriveDistancePID(drivetrain, 0.5),
      new OpenClaw(claw),
      new DriveDistancePID(drivetrain, 0.5),
      new RotateArmToAngleConstant(arm, 0)
    );

    addRequirements(drivetrain, arm, claw);
  }
          
  
}

