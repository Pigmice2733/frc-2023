// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class SpinIntakeWheels extends CommandBase {

  private final Claw claw;
  private final boolean intakeDirection;

  public SpinIntakeWheels(Claw claw, boolean intakeDirection, double time) {
    this.claw = claw;
    this.intakeDirection = intakeDirection;

    withTimeout(time);
    addRequirements(claw);  
  }

  @Override
  public void initialize() {
    claw.startMotors(intakeDirection);
  }

  @Override
  public void end(boolean interrupted) {
    claw.stopMotors();
  }
}
