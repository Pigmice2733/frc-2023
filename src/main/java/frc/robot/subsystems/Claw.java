// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.ClawConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  Double multiplier = ClawConfig.speedMultipler;

  private final CANSparkMax claw = new CANSparkMax(ClawConfig.clawPort, MotorType.kBrushless);

  /** Creates a new Claw. */
  public Claw() {
    claw.restoreFactoryDefaults();
  }

  public void rotateClaw(double speed){
    claw.set(speed * multiplier);
  }

}
