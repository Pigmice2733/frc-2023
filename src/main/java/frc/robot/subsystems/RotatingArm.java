// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.ClawConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  double multiplier = ClawConfig.speedMultipler;

  private final CANSparkMax claw = new CANSparkMax(ClawConfig.clawPort, MotorType.kBrushless);

  /** Creates a new rotating arm. */
  public RotatingArm() {
    claw.restoreFactoryDefaults();
    claw.getEncoder().setPositionConversionFactor(ClawConfig.rotationConversion);
  }

  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void rotateClaw(double speed){
    claw.set(speed * multiplier);
  }

  /**
   * Get the current angle of the arm.
   */
  public double getAngle(){
    return claw.getEncoder().getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation.
   */
  public void resetEncoder(){
    claw.getEncoder.setPosition(0);
  }
}
