// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.RotatingArm;

public class RotateArmManaul extends CommandBase {
  private final RotatingArm arm;
  private final DoubleSupplier speed;

  public RotateArmManaul(RotatingArm arm, DoubleSupplier speed) {
    this.arm = arm;
    this.speed = speed;

    addRequirements(arm);
  }

  @Override
  public void execute() {
    arm.setTargetOutput(speed.getAsDouble());
  }
}
