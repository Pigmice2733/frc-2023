// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.Constants;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.elevator.RaiseElevatorToHeight;
import frc.robot.commands.rotatingArm.RotateArmToAngle;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

public class ScoreObjectMidCube extends SequentialCommandGroup {
  /**
   * Automatically scores a cube in a middle-level node, assuming the robot is already lined up and pushed up against the grid.
   * @param arm the arm subsystem
   * @param elevator the elevator subsystem
   * @param claw the claw subsystem
   * @param drivetrain the drivetrain subsystem
   */
  public ScoreObjectMidCube(RotatingArm arm, Elevator elevator, Claw claw, Drivetrain drivetrain) {
    addRequirements(arm, elevator, claw);
    addCommands(
      new MoveClawToPoint(arm, elevator, 34.0, distance), // distance depends on robot length
      new MoveClawToPoint(arm, elevator, 24.0, distance), // distance depends on robot length
      new InstantCommand(claw::openClaw),
      new DriveDistance(drivetrain, -0.5),
      new RaiseElevatorToHeight(Constants.RotatingArmConfig.armLength, elevator),
      new RotateArmToAngle(0, arm),
      new InstantCommand(claw::closeClaw)
    );
  }
}
