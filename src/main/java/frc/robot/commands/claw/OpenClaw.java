// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class OpenClaw extends CommandBase {
private final Claw claw;
  public OpenClaw(Claw claw) {
    this.claw = claw;
    addRequirements(claw);
  }

  @Override
  public void initialize() {
    // TODO: Open claw
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    // TODO: Return true when claw is open
    return false;
  }
}
