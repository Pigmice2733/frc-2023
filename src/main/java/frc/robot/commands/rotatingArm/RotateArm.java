// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.RotatingArm;

public class RotateArm extends CommandBase {
  private final RotatingArm arm;
  private final DoubleSupplier speed;

  private EnableBrake enableBrakeCommand;
  private DisableBrake disableBrakeCommand;

  public RotateArm(RotatingArm arm, DoubleSupplier speed) {
    this.arm = arm;
    this.speed = speed;

    enableBrakeCommand = new EnableBrake(arm);
    disableBrakeCommand = new DisableBrake(arm);
    addRequirements(arm);
  }

  @Override
  public void execute() {
    // double inputSpeed = speed.getAsDouble();

    // if (arm.brakeEnabled() && inputSpeed > DrivetrainConfig.axisThreshold) {
    //   if (!EnableBrake.currentlyRunning) {
    //     enableBrakeCommand.schedule();
    //   }
    //   return;
    // }

    // if (!arm.brakeEnabled() && inputSpeed < DrivetrainConfig.axisThreshold) {
    //   disableBrakeCommand.schedule();
    //   return;
    // }

    arm.setClawSpeed(speed.getAsDouble());
  }
}
