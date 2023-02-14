// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RotatingArm;

public class RotateArmToAngleSimple extends CommandBase {
  private final RotatingArm arm;
  private final double targetAngle;
  private boolean reverse;

  /** Creates a new RotateArmToAngleSimple. */
  public RotateArmToAngleSimple(RotatingArm arm, double targetAngle) {
    this.arm = arm;
    this.targetAngle = targetAngle;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    reverse = (arm.getAngle() > targetAngle) ? true : false;
    System.out.println(reverse);
    SmartDashboard.putBoolean("Command Running", true);
  }

  @Override
  public void execute() {
    arm.setTargetOutput(0.5 * (reverse ? -1 : 1));
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Command Running", false);
    System.out.println("Command done");
  }

  @Override
  public boolean isFinished() {
    if (reverse)
      return arm.getAngle() < targetAngle;
    else 
      return arm.getAngle() > targetAngle;
  }
}