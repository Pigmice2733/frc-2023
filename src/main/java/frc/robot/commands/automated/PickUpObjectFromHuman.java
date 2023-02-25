// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveDistanceConstant;
import frc.robot.commands.rotatingArm.RotateArmToAngleConstant;
import frc.robot.commands.rotatingArm.RotateArmToAnglePID;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class PickUpObjectFromHuman extends SequentialCommandGroup {
  /**
   * Automatically pick up an object from the DoubleSubstation shelf, assuming the robot is already aligned.
   * @param arm the arm subsystem
   * @param claw the claw subsystem
   */
  public PickUpObjectFromHuman(RotatingArm arm, Claw claw, Drivetrain drivetrain) {
    double armAngle = arm.armHeightToAngle(Units.inchesToMeters(40.0));
    addCommands(
      new DriveDistanceConstant(drivetrain, -0.5),
      new RotateArmToAngleConstant(arm, armAngle),
      new InstantCommand(claw::openClaw),
      new DriveDistanceConstant(drivetrain, 0.5),
      new InstantCommand(claw::closeClaw),
      new DriveDistanceConstant(drivetrain, -0.5)
    );
    addRequirements(arm, claw);
  }
}
