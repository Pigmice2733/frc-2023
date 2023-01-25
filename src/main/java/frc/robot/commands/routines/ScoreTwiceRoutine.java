// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoreObject;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

/** Scores the object the robot starts with, then the robot drives to pick up another object to score */
public class ScoreTwiceRoutine extends SequentialCommandGroup {
  public ScoreTwiceRoutine(Drivetrain drivetrain, RotatingArm arm, Elevator elevator, Claw claw) {
    addCommands(
      new ScoreObject(arm, elevator, claw),
      // new PickUpNewObject(),
      new ScoreObject(arm, elevator, claw)
    );
  }
}
