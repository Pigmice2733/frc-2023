// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class RaiseElevatorManual extends CommandBase {
  private final Elevator elevator;
  private final DoubleSupplier speed;

  public RaiseElevatorManual(Elevator elevator, DoubleSupplier speed) {
    this.elevator = elevator;
    this.speed = speed;

    addRequirements(elevator);
  }

  @Override
  public void execute() {
    elevator.outputToMotors(speed.getAsDouble());
  }
}

