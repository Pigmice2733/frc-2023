// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.automated.ScoreObject;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreAndLeave extends SequentialCommandGroup {
  /** Creates a new ScoreAndLeave. */
  public ScoreAndLeave(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    addCommands(
      new ScoreObject(drivetrain, arm, claw, true),
      new DriveDistancePID(drivetrain, -3));
  }
}
