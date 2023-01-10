// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Timer;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class BangBangBalance extends CommandBase {
  BangBangController controller;
  Drivetrain drivetrain;

  Timer endTimer = new Timer();
  boolean timerRunning;
  float timeTimerStarted;

  /** Creates a new BangBangBalance. */
  public BangBangBalance(Drivetrain drivetrain) {
    controller = new BangBangController(0);
    controller.setTolerance(0.1);
    this.drivetrain = drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = controller.calculate(drivetrain.getPitch()) * DrivetrainConfig.autoBalanceSpeed;
    drivetrain.tankDrive(speed, speed);

    //updateTimer();
  }

  // public void updateTimer() {
  //   if (controller.atSetpoint() && timerRunning)
  //     return;

  //   if (controller.atSetpoint()) 
  //   {
  //     timeTimerStarted = System.currentTimeMillis();
  //     timerRunning = true;
  //   }
    
  //   if (!controller.atSetpoint()) {
  //     timerRunning = false;
  //   }
  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return ((timeTimerStarted-System.currentTimeMillis())/1000) < 3;
    return false;
  }
}
