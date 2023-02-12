// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.rotatingArm.RotateArmToAnglePID;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.RotatingArm;

public class ScoreObjectFloor extends SequentialCommandGroup {
  /**
   * Automatically scores a cone or cube in a floor-level node, assuming the robot
   * is already lined up and pushed up against the grid.
   * 
   * @param arm        the arm subsystem
   * @param elevator   the elevator subsystem
   * @param claw       the claw subsystem
   * @param drivetrain the drivetrain subsystem
   */
  public ScoreObjectFloor(RotatingArm arm, Elevator elevator, Claw claw, Drivetrain drivetrain) {
    // addRequirements(arm, elevator, claw);
    addCommands(new WaitCommand(0.5));
    // addCommands(
    // //new MoveClawToPoint(arm, elevator, 5.0, 0.0), // TODO correct distance
    // new InstantCommand(claw::openClaw),
    // new DriveDistance(drivetrain, -0.5),
    // //new RaiseElevatorToHeightPID(Constants.RotatingArmConfig.armLength,
    // elevator),
    // new RotateArmToAnglePID(0, arm),
    // new InstantCommand(claw::closeClaw)
    // );
  }
}
