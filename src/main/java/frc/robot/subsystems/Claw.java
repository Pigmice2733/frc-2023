// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.ClawConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  private final Compressor compressor = new Compressor(20, PneumaticsModuleType.REVPH);

  private final DoubleSolenoid leftPiston = new DoubleSolenoid(20, PneumaticsModuleType.REVPH,
      ClawConfig.leftPistonPorts[0], ClawConfig.leftPistonPorts[1]);
  private final DoubleSolenoid rightPiston = new DoubleSolenoid(20, PneumaticsModuleType.REVPH,
      ClawConfig.rightPistonPorts[0], ClawConfig.rightPistonPorts[1]);

  private final CANSparkMax leftMotor = new CANSparkMax(ClawConfig.leftMotorPort, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(ClawConfig.rightMotorPort, MotorType.kBrushless);

  public Claw() {
    compressor.enableDigital();
    leftPiston.set(Value.kReverse);
    rightPiston.set(Value.kForward);

    leftMotor.restoreFactoryDefaults();
    rightMotor.restoreFactoryDefaults();

    leftMotor.setInverted(false);
    rightMotor.setInverted(true);
  }

  public void closeClaw(boolean stopMotors) {
    leftPiston.set(Value.kReverse);
    rightPiston.set(Value.kForward);

    if (stopMotors)
      stopMotors();
  }

  public void openClaw(boolean startMotors) {
    leftPiston.set(Value.kForward);
    rightPiston.set(Value.kReverse);

    if (startMotors)
      startMotors(false);
  }

  private void outputToMotors(double output) {
    leftMotor.set(output);
    rightMotor.set(output);
  }

  public void startMotors(boolean intakeDirection) {
    outputToMotors(ClawConfig.motorSpeed * (intakeDirection ? -1.0 : 1.0));
  }

  public void stopMotors() {
    outputToMotors(0.0);
  }
}
