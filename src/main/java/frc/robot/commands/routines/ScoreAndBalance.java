// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ScoreObject;
import frc.robot.commands.drivetrain.AutoBalance;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

/** A routine to be run in auto. Scores the object the robot starts with then balances. Robot needs to be placed in line with charge station */
public class ScoreAndBalance extends SequentialCommandGroup {
  public ScoreAndBalance(Drivetrain drivetrain, RotatingArm arm, Elevator elevator, Claw claw) {
    addCommands(
      new ScoreObject(arm, elevator, claw),
      new AutoBalance(drivetrain)
    );
  }
}
