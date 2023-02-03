// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.RotatingArmConfig;
import frc.robot.commands.rotatingArm.DisableBrake;
import frc.robot.commands.rotatingArm.EnableBrake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(RotatingArmConfig.motorPort, MotorType.kBrushless);
  private final DoubleSolenoid brake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RotatingArmConfig.brakePort[0], RotatingArmConfig.brakePort[1]);
  private final Compressor compressor;

  private boolean brakeEnabled = false;

  private double targetMotorOutput = 0;

  private EnableBrake enableBrakeCommand = new EnableBrake(this);
  private DisableBrake disableBrakeCommand = new DisableBrake(this);

  public RotatingArm() {
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);

    motor.restoreFactoryDefaults();
    motor.getEncoder().setPositionConversionFactor(RotatingArmConfig.rotationConversion);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Angle", getAngle());
    applyClawOutput();
  }

  private void applyClawOutput() {
    if (!brakeEnabled && Math.abs(targetMotorOutput) < DrivetrainConfig.axisThreshold) {
      enableBrakeCommand.schedule();
      outputToMotor(0);
      return;
    }

    if (brakeEnabled && Math.abs(targetMotorOutput) > DrivetrainConfig.axisThreshold) {
      disableBrakeCommand.schedule();
      outputToMotor(0);
      return;
    }

    if (brakeEnabled)
      return;

    outputToMotor(targetMotorOutput);
  }

  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void setTargetOutput(double speed){
    targetMotorOutput = speed;
  }

  private void outputToMotor(double output) {
    if (brakeEnabled)
      return;
    motor.set(output * RotatingArmConfig.speedMultipler);
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
    outputToMotor(0);
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
