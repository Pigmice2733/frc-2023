// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

public class RaiseElevatorToHeightSimple extends CommandBase {
  private final Elevator elevator;
  private final double targetHeight;
  private boolean reverse;

  /** Creates a new RotateArmToAngleSimple. */
  public RaiseElevatorToHeightSimple(Elevator elevator, double targetHeight) {
    this.elevator = elevator;
    this.targetHeight = targetHeight;

    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    reverse = (elevator.getHeight() > targetHeight) ? true : false;
    System.out.println(reverse);
    SmartDashboard.putBoolean("Command Running", true);
  }

  @Override
  public void execute() {
    elevator.outputToMotors(0.5 * (reverse ? -1 : 1));
  }

  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Command Running", false);
  }

  @Override
  public boolean isFinished() {
    if (reverse)
      return elevator.getHeight() < targetHeight;
    else 
      return elevator.getHeight() > targetHeight;
  }
}