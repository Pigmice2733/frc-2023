// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.subsystems.RotatingArm;

public class RotateArmToScoreHeight extends CommandBase {
  private final RotatingArm arm;
  //private RotateArmToAngleConstant rotateCommand;

  public RotateArmToScoreHeight(RotatingArm arm) {
    this.arm = arm;
  }

  @Override
  public void initialize() {
    // double height = 0.0; // meters

    TargetLocation targetLocation = RuntimeTrajectoryGenerator.getTargetType();

    // Old
    // if (selectedScoreHeight == ScoreHeight.Mid && targetLocation ==
    // TargetLocation.Center) {
    // height = Units.inchesToMeters(24.0);
    // } // mid cube
    // else if (selectedScoreHeight == ScoreHeight.Mid && targetLocation !=
    // TargetLocation.Center) {
    // height = Units.inchesToMeters(36.0);
    // } // mid cone
    // else if (selectedScoreHeight == ScoreHeight.High && targetLocation ==
    // TargetLocation.Center) {
    // height = Units.inchesToMeters(36.0);
    // } // high cube
    // else if (selectedScoreHeight == ScoreHeight.High && targetLocation !=
    // TargetLocation.Center) {
    // height = Units.inchesToMeters(38.0);
    // } // high cone
    // else {
    // height = 0;
    // } // floor

    // if (selectedScoreHeight == ScoreHeight.Floor) {
    // height = Units.inchesToMeters(0.0);
    // } // mid cube
    // if (selectedScoreHeight == ScoreHeight.Mid) {
    // height = Units.inchesToMeters(34);
    // } // mid cube
    // else if (selectedScoreHeight == ScoreHeight.High) {
    // height = Units.inchesToMeters(46.0);
    // } // mid cone
    // else if (selectedScoreHeight == ScoreHeight.HumanPlayer) {
    // height = Units.inchesToMeters(38.0);
    // } // high cube

    double targetAngle = 3;
    if (selectedScoreHeight == ScoreHeight.Floor) {
      targetAngle = 3;
    }
    if (selectedScoreHeight == ScoreHeight.Mid) {
      targetAngle = 45;
    } else if (selectedScoreHeight == ScoreHeight.High) {
      targetAngle = 65;
    } else if (selectedScoreHeight == ScoreHeight.HumanPlayer) {
      targetAngle = 60;
    }

    //rotateCommand = new RotateArmToAngleConstant(arm, targetAngle);
    arm.setSetpoint(targetAngle);
  }

  @Override
  public boolean isFinished() {
    // if (rotateCommand == null)
    //   return true;

    // return rotateCommand.isFinished();
    return arm.atSetpoint();
  }

  public enum ScoreHeight {
    Floor,
    Mid,
    High,
    HumanPlayer
  }

  private static ScoreHeight selectedScoreHeight = ScoreHeight.Floor;
  private static GenericEntry targetHeightEntry = RobotContainer.addToDriverTab("Target Height",
      selectedScoreHeight.toString());

  public static void setScoreHeight(ScoreHeight scoreHeight) {
    selectedScoreHeight = scoreHeight;
    targetHeightEntry.setString(scoreHeight.toString());
  }

  public RotateArmToScoreHeight withHeight(ScoreHeight scoreHeight) {
    setScoreHeight(scoreHeight);
    return this;
  }

  public static ScoreHeight getScoreHeight() {
    return selectedScoreHeight;
  }
}
