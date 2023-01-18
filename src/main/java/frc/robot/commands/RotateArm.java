// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.RotateingArm;

public class RotateArm extends CommandBase {
  RotateingArm claw;
  DoubleSupplier speed;

  public RotateArm(RotateingArm claw, DoubleSupplier speed) {
    this.claw = claw;
    this.speed = speed;
    addRequirements(claw);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    claw.rotateClaw(speed.getAsDouble());
  }

}
