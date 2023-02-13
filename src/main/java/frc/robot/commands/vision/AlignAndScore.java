// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class AlignAndScore extends SequentialCommandGroup {
  /** Creates a new AlignAndScore. */
  public AlignAndScore(Vision vision, Drivetrain drivetrain) {
    addCommands(
      new AlignToScore(vision, drivetrain)
      // TODO: Run selected score command
    );

    addRequirements(vision, drivetrain);
  }
}
