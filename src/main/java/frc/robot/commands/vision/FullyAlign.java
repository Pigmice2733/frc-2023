// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.autoDrive.DriveDistancePID;
import frc.robot.commands.drivetrain.autoDrive.TurnToAnglePID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class FullyAlign extends SequentialCommandGroup {
  /** Creates a new FullyAlign. */
  public FullyAlign(Drivetrain drivetrain, Vision vision) {
    addCommands(
        new AlignToScore(vision, drivetrain),
        new TurnToAnglePID(drivetrain, 0),
        new DriveDistancePID(drivetrain, 1),
        new InstantCommand(() -> System.out.println("COMMAND ENDED")));
    addRequirements(drivetrain, vision);
  }
}
