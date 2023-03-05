// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;
import frc.robot.subsystems.Vision;

public class AlignAndScore extends SequentialCommandGroup {
  public AlignAndScore(Vision vision, Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    addCommands(
      new FullyAlign(drivetrain, vision),
      new ScoreObject(drivetrain, arm, claw, true, true)
    );
    addRequirements(vision, drivetrain);
  }
}
