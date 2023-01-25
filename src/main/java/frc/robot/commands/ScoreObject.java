// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

/** Automatically scores a cone or cube assuming the robot is already lined up and pushed up against the grid */
public class ScoreObject extends SequentialCommandGroup {
  public ScoreObject(RotatingArm arm, Elevator elevator, Claw claw) { // TODO: Add support for scoring cones and cubes at different heights
    addRequirements(arm, elevator, claw);
    addCommands(); // TODO: Add commands to score an object
  }
}
