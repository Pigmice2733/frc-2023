// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.RotatingArmConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  private final CANSparkMax driveMotor = new CANSparkMax(RotatingArmConfig.driveMotorPort, MotorType.kBrushless);
  private final CANSparkMax followMotor = new CANSparkMax(RotatingArmConfig.followMotorPort, MotorType.kBrushless);
  private final CANSparkMax encoderController = new CANSparkMax(RotatingArmConfig.encoderControllerPort, MotorType.kBrushed);

  private final ShuffleboardTab armTab;
  private final GenericEntry /*topSwitchEntry, bottomSwitchEntry,*/ angleEntry, motorOutputEntry;

  private final DoubleSolenoid brake = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RotatingArmConfig.brakePort[0],
      RotatingArmConfig.brakePort[1]);

  private boolean brakeEnabled = false;

  private double targetMotorOutput = 0;

  //private final DigitalInput topLimitSwitch;
  //private final DigitalInput bottomLimitSwitch;

  // public boolean getTopSwitch() {
  //   return !topLimitSwitch.get();
  // }

  // public boolean getBottomSwitch() {
  //   return !bottomLimitSwitch.get();
  // }

  public RotatingArm() {
    // topLimitSwitch = new DigitalInput(RotatingArmConfig.topLimitSwitchID);
    // bottomLimitSwitch = new DigitalInput(RotatingArmConfig.bottomLimitSwitchID);

    driveMotor.restoreFactoryDefaults();
    followMotor.restoreFactoryDefaults();
    followMotor.follow(driveMotor);

    driveMotor.setInverted(false);
    followMotor.setInverted(false);

    encoderController.getEncoder(Type.kQuadrature, 8192).setPositionConversionFactor(360); // Converts rotatings to degrees
    resetEncoder();

    armTab = Shuffleboard.getTab("armTab");
    // topSwitchEntry = armTab.add("Top Switch", false).getEntry();
    // bottomSwitchEntry = armTab.add("Bottom Switch", false).getEntry();
    angleEntry = armTab.add("Arm Angle", 0).getEntry();
    motorOutputEntry = armTab.add("Motor Output", 0).getEntry();
  }

  @Override
  public void periodic() {
    updateShuffleboard();
    applyClawOutput();
  }

  private void applyClawOutput() {
    double motorOutput = targetMotorOutput;

    // if (getTopSwitch())
    //   motorOutput = Math.min(0, motorOutput);
    // if (getBottomSwitch())
    //   motorOutput = Math.max(0, motorOutput);

    if (getAngle() > RotatingArmConfig.maxArmAngleDegrees || getAngle() < RotatingArmConfig.minArmAngleDegrees)
      motorOutput = 0;

    if (!brakeEnabled && Math.abs(motorOutput) < 0.01) {
      enableBrake();
      outputToMotor(0);
      return;
    }

    if (brakeEnabled && Math.abs(motorOutput) > 0.01) {
      disableBrake();
      outputToMotor(0);
      return;
    }

    if (brakeEnabled) {
      outputToMotor(0);
      return;
    }

    outputToMotor(motorOutput);
  }

  /**
   * Rotate the arm.
   * 
   * @param speed the speed to rotate at
   */
  public void setTargetOutput(double speed) {
    targetMotorOutput = speed;
  }

  private void outputToMotor(double output) {
    // TODO: Remove clamp after initial testing
    output = MathUtil.clamp(output, -0.1, 0.1);
    motorOutputEntry.setDouble(output);
    driveMotor.set(output);
  }

  /**
   * Converts the given height to the angle for the arm; i.e., when the arm is
   * rotated to the output angle the claw will be at the input height.
   * 
   * @param height the height of the claw
   * @return the angle of the arm
   */
  public double armHeightToAngle(double height) {
    height -= RotatingArmConfig.armHeightMeters - RotatingArmConfig.armLengthMeters;
    double clampedHeight = MathUtil.clamp(height / (RotatingArmConfig.armLengthMeters) - 1, -1.0, 1.0);
    double armAngle = Math.asin(clampedHeight) * (180 / Math.PI) + 90;
    return armAngle;
  }

  private void updateShuffleboard() {
    angleEntry.setDouble(getAngle());
    // topSwitchEntry.setBoolean(topLimitSwitch.get());
    // bottomSwitchEntry.setBoolean(bottomLimitSwitch.get());
  }

  /** Get the current angle of the arm. */
  public double getAngle() {
    return encoderController.getEncoder(Type.kQuadrature, 8192).getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation. This means the arm is pointed
   * straight down.
   */
  public void resetEncoder() {
    encoderController.getEncoder(Type.kQuadrature, 8192).setPosition(0);
  }

  public void enableBrake() {
    brake.set(Value.kReverse);
    outputToMotor(0);
    brakeEnabled = true;
  }

  public void disableBrake() {
    brake.set(Value.kForward);
    brakeEnabled = false;
  }

  public boolean getBrakeEnabled() {
    return brakeEnabled;
  }
}
