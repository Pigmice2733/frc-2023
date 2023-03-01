// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Claw;
import frc.robot.subsystems.RotatingArm;
import frc.robot.commands.rotatingArm.RotateArmToAnglePID;

public class PickUpObjectFromGround extends SequentialCommandGroup {
  /**
   * Picks up a cone or cube, assuming the robot is already lined up, then lifts
   * the arm to horizontal.
   * 
   * @param arm  the rotating-arm subsystem
   * @param claw the claw subsystem
   */
  public PickUpObjectFromGround(RotatingArm arm, Claw claw) {
    addRequirements(arm, claw);

    addCommands(
        new InstantCommand(() -> claw.openClaw(true)),
        new RotateArmToAnglePID(arm.armHeightToAngle(Units.inchesToMeters(0.0)), arm),
        new InstantCommand(() -> claw.closeClaw(true)),
        new RotateArmToAnglePID(90, arm));
  }
}
