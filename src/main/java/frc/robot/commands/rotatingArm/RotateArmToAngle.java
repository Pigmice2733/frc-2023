// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.RotatingArm;

public class RotateArmToAngle extends CommandBase {
  private final RotatingArm arm;
  private double targetAngle;
  private boolean useSelectedHeight;

  public RotateArmToAngle(RotatingArm arm) {
    this.arm = arm;
    useSelectedHeight = true;
  }

  public RotateArmToAngle(RotatingArm arm, ArmHeight height) {
    this.arm = arm;
    targetAngle = scoreHeightToAngle(height);
  }

  public RotateArmToAngle(RotatingArm arm, double angle) {
    this.arm = arm;
    this.targetAngle = angle;
  }

  @Override
  public void initialize() {
    if (useSelectedHeight)
      targetAngle = scoreHeightToAngle(selectedScoreHeight);
      
    arm.setGoal(targetAngle);
    SmartDashboard.putBoolean("Command Running", true);
  }

  @Override
  public void end(boolean interupted) {
    SmartDashboard.putBoolean("Command Running", false);

  }

  @Override
  public boolean isFinished() {
    return arm.atGoal();
  }

  public enum ArmHeight {
    Zero,
    Floor,
    Mid,
    High,
    HumanPlayer
  }

  public static double scoreHeightToAngle(ArmHeight scoreHeight) {
    switch (scoreHeight) {
      case Zero:
        return 0;
      case Floor:
        return 20;
      case Mid:
        return 65;
      case High:
        return 82;
      case HumanPlayer:
        return 65;
      default:
        return 20;
    }
  }

  private static ArmHeight selectedScoreHeight = ArmHeight.Floor;
  private static GenericEntry targetHeightEntry = RobotContainer.addToDriverTab("Target Height",
      selectedScoreHeight.toString(), 4, 1);

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
