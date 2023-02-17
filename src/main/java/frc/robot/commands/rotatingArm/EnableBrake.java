// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.rotatingArm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.RotatingArm;

public class EnableBrake extends InstantCommand {
  RotatingArm rotatingArm;
  public EnableBrake(RotatingArm rotatingArm) {
    this.rotatingArm = rotatingArm;
  }

  @Override
  public void initialize() {
    rotatingArm.enableBrake();
  }
}
