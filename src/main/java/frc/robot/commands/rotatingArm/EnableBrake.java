// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.RotatingArm;

public class EnableBrake extends SequentialCommandGroup {
  public static boolean currentlyRunning = false;

  public EnableBrake(RotatingArm rotatingArm) {
    addCommands(
      new InstantCommand( () -> { currentlyRunning = true; }),
      new InstantCommand( () -> { rotatingArm.setClawSpeed(0.05); }),
      new WaitCommand(0.25),
      new InstantCommand( () -> { rotatingArm.enableBrake(); }),
      new InstantCommand( () -> { rotatingArm.setClawSpeed(0); }),
      new InstantCommand( () -> { currentlyRunning = false; })
    );
    addRequirements(rotatingArm);
  }
}
