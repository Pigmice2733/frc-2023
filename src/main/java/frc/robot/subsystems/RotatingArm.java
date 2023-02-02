// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.RotatingArmConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(RotatingArmConfig.motorPort, MotorType.kBrushless);
  private final DoubleSolenoid brake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RotatingArmConfig.brakePort[0], RotatingArmConfig.brakePort[1]);

  private boolean brakeEnabled = false;

  public RotatingArm() {
    motor.restoreFactoryDefaults();
    motor.getEncoder().setPositionConversionFactor(RotatingArmConfig.rotationConversion);
  }

  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void setClawSpeed(double speed){
    speed = brakeEnabled ? 0 : speed;
    motor.set(speed * RotatingArmConfig.speedMultipler);
  }

  /** Get the current angle of the arm. */
  public double getAngle(){
    return motor.getEncoder().getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation. This means the arm is pointed straight down.
   */
  public void resetEncoder(){
    motor.getEncoder().setPosition(0);
  }

  public void enableBrake() {
    brake.set(Value.kForward);
    setClawSpeed(0);
    brakeEnabled = true;
  }

  public void disableBrake() {
    brake.set(Value.kReverse);
    brakeEnabled = false;
  }

  public boolean brakeEnabled() {
    return brakeEnabled;
  }
}
