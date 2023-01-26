// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.RotatingArmConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  double multiplier = RotatingArmConfig.speedMultipler;

  private final CANSparkMax motor = new CANSparkMax(RotatingArmConfig.motorPort, MotorType.kBrushless);

  /** Creates a new rotating arm. */
  public RotatingArm() {
    motor.restoreFactoryDefaults();
    motor.getEncoder().setPositionConversionFactor(RotatingArmConfig.rotationConversion);
  }

  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void rotateClaw(double speed){
    motor.set(speed * multiplier);
  }

  /**
   * Get the current angle of the arm.
   */
  public double getAngle(){
    return motor.getEncoder().getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation. This means the arm is pointed straight down.
   */
  public void resetEncoder(){
    motor.getEncoder().setPosition(0);
  }
}
