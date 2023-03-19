// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;

public class ShakeClaw extends SequentialCommandGroup {
  /** Creates a new ShakeClaw. */
  public ShakeClaw(Drivetrain drivetrain) {
    addCommands(
      new InstantCommand(() -> drivetrain.driveVoltages(4, 4)),
      new WaitCommand(0.5),
      new InstantCommand(() -> drivetrain.driveVoltages(-4, -4)),
      new WaitCommand(0.5),
      new InstantCommand(() -> drivetrain.arcadeDrive(0, 0))
    );
  }
}
