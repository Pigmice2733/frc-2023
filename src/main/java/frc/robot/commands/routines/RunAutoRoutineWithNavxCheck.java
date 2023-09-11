// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class RunAutoRoutineWithNavxCheck extends CommandBase {
  private final Command command;
  private final Drivetrain drivetrain;

  public RunAutoRoutineWithNavxCheck(Command command, Drivetrain drivetrain) {
    this.command = command;
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    command.schedule();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    if (command != null)
      command.cancel();
  }

  @Override
  public boolean isFinished() {
    return command.isFinished() || !drivetrain.navxConnected();
  }
}
