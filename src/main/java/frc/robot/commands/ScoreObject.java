// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

/** Automatically scores a cone or cube assuming the robot is already lined up with the grid */
public class ScoreObject extends CommandBase {
  public ScoreObject(RotatingArm arm, Elevator elevator, Claw claw) {
    addRequirements(arm, elevator, claw);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
