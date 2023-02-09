// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;
import frc.robot.Constants;
import frc.robot.commands.elevator.MoveClawToPoint;
import frc.robot.commands.elevator.RaiseElevatorToHeightPID;
import frc.robot.commands.rotatingArm.RotateArmToAnglePID;

public class PickUpObjectFromGround extends SequentialCommandGroup {
  /**
   * Automatically picks up a cone or cube assuming the robot is already lined up.
   * @param arm the rotating-arm subsystem
   * @param elevator the elevator subsystem
   * @param claw the claw subsystem
   */
  public PickUpObjectFromGround(RotatingArm arm, Elevator elevator, Claw claw) {
    addRequirements(arm, elevator, claw);

    addCommands(
      new InstantCommand(claw::openClaw),
      new MoveClawToPoint(arm, elevator, 5.0, 0.0), // TODO distance depends on robot specs and what we want
      new InstantCommand(claw::closeClaw),
      new RaiseElevatorToHeightPID(Constants.RotatingArmConfig.armLength, elevator),
      new RotateArmToAnglePID(0, arm)
    );
  }
}
