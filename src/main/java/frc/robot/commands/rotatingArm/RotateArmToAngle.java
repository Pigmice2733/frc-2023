// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.RotatingArm;

public class RotateArmToAngle extends CommandBase {
  private final RotatingArm arm;
  private final double targetAngle;

  public RotateArmToAngle(RotatingArm arm) {
    this(arm, selectedScoreHeight);
  }
  public RotateArmToAngle(RotatingArm arm, ArmHeight height) {
    this(arm, scoreHeightToAngle(height));
  }
  public RotateArmToAngle(RotatingArm arm, double angle) {
    this.arm = arm;
    this.targetAngle = angle;
  }

  @Override
  public void initialize() {
    arm.setSetpoint(targetAngle);
  }

  @Override
  public boolean isFinished() {
    return arm.atSetpoint();
  }

  public enum ArmHeight {
    Floor,
    Mid,
    High,
    HumanPlayer
  }

  public static double scoreHeightToAngle(ArmHeight scoreHeight) {
    switch(scoreHeight) {
      case Floor:
        return 5;
      case Mid:
        return 45;
      case High:
        return 95;
      case HumanPlayer:
        return 85;
      default:
        return 0;
    }
  }

  private static ArmHeight selectedScoreHeight = ArmHeight.Floor;
  private static GenericEntry targetHeightEntry = RobotContainer.addToDriverTab("Target Height",
      selectedScoreHeight.toString());

  public static void setScoreHeight(ArmHeight scoreHeight) {
    selectedScoreHeight = scoreHeight;
    targetHeightEntry.setString(scoreHeight.toString());
  }

  public RotateArmToAngle withHeight(ArmHeight scoreHeight) {
    setScoreHeight(scoreHeight);
    return this;
  }

  public static ArmHeight getScoreHeight() {
    return selectedScoreHeight;
  }
}
