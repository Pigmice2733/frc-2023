// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.automated.ScoreObject;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreAndBalance extends SequentialCommandGroup {
  public ScoreAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    addCommands(
        new ScoreObject(drivetrain, arm, claw),
        new BalanceRoutine(drivetrain, true));
    
    addRequirements(drivetrain, arm, claw);
  }
}
