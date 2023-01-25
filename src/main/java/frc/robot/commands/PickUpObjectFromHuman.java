// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

/** Automatically picks up a cone or cube assuming the robot is already lined up */
public class PickUpObjectFromHuman extends SequentialCommandGroup {
  public PickUpObjectFromHuman(RotatingArm arm, Elevator elevator, Claw claw) {
    addRequirements(arm, elevator, claw);

    addCommands(); // TODO: Add commands to pick up an object from the double substation
  }
}
