// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ClawConfig;

public class Claw extends SubsystemBase {
  private final Compressor compressor = new Compressor(20, PneumaticsModuleType.REVPH);

  private final DoubleSolenoid leftPiston = new DoubleSolenoid(20, PneumaticsModuleType.REVPH,
      ClawConfig.leftPistonPorts[0], ClawConfig.leftPistonPorts[1]);
  private final DoubleSolenoid rightPiston = new DoubleSolenoid(20, PneumaticsModuleType.REVPH,
      ClawConfig.rightPistonPorts[0], ClawConfig.rightPistonPorts[1]);

  private final CANSparkMax leftMotor = new CANSparkMax(ClawConfig.leftMotorPort, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(ClawConfig.rightMotorPort, MotorType.kBrushless);

  public AnalogPotentiometer distanceSensor = new AnalogPotentiometer(0);

  private static final double DISTANCE_THRESHOLD = 0.5; // higher = closer but only up to a point
  private boolean isOpen = false;
  private double openTimestamp = 0;

  public Claw() {
    // compressor.disable();
    // compressor.close();
    leftPiston.set(Value.kForward);
    rightPiston.set(Value.kReverse);

    leftMotor.restoreFactoryDefaults();
    rightMotor.restoreFactoryDefaults();

    leftMotor.setInverted(false);
    rightMotor.setInverted(true);

    SmartDashboard.putNumber("Output multiplier", 1);
  }

  @Override
  public void periodic() {
    if (this.isOpen() && !didJustOpen() && hasCubeWhileOpen()) {
      this.stopMotors();
    }
  }

  private double getDistance() {
    return this.distanceSensor.get();
  }

  public boolean didJustOpen() {
    double currentTime = Timer.getFPGATimestamp();
    return (currentTime - openTimestamp) < 1;
  }

  public boolean hasCubeWhileOpen() {
    double averageVelocity = Math.abs(leftMotor.getEncoder().getVelocity() + rightMotor.getEncoder().getVelocity())
        / 2d;
    SmartDashboard.putNumber("Average Velocity", averageVelocity);
    return averageVelocity < 10;
  }

  // called in RobotContainer::periodic
  public boolean canGrabGamepiece() {
    return (this.getDistance() > DISTANCE_THRESHOLD || (this.isOpen()
        && hasCubeWhileOpen()));
  }

  // called in RobotContainer::periodic
  public boolean isOpen() {
    return this.isOpen;
  }

  public void closeClaw(boolean stopMotors) {
    leftPiston.set(Value.kForward);
    rightPiston.set(Value.kReverse);

    isOpen = false;

    if (stopMotors)
      stopMotors();
  }

  public Command closeClawCommand(boolean stopMotors) {
    return new InstantCommand(() -> closeClaw(stopMotors));
  }

  public void openClaw(boolean startMotors) {
    leftPiston.set(Value.kReverse);
    rightPiston.set(Value.kForward);

    openTimestamp = Timer.getFPGATimestamp();

    isOpen = true;

    if (startMotors)
      startMotors(false);
  }

  public Command openClawCommand(boolean startMotors) {
    return Commands.sequence(
        new InstantCommand(() -> openClaw(false)),
        new InstantCommand(() -> outputToMotors(-0.1)),
        new WaitCommand(0.2),
        new InstantCommand(() -> openClaw(startMotors)));
  }

  private void outputToMotors(double output) {
    output *= SmartDashboard.getNumber("Output multiplier", 1);
    leftMotor.set(output);
    rightMotor.set(output);
    SmartDashboard.putNumber("Claw output", output);
  }

  public void startMotors(boolean intakeDirection) {
    SmartDashboard.putBoolean("Claw Motors", true);
    outputToMotors(intakeDirection ? -ClawConfig.outtakeMotorSpeed : ClawConfig.intakeMotorSpeed);
  }

  public void stopMotors() {
    SmartDashboard.putBoolean("Claw Motors", false);
    outputToMotors(0.0);
  }
}
