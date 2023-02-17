// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.claw;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Claw;

public class OpenClaw extends InstantCommand {
private final Claw claw;
  public OpenClaw(Claw claw) {
    this.claw = claw;
    addRequirements(claw);
  }

  @Override
  public void initialize() {
    claw.openClaw();
  }
}
