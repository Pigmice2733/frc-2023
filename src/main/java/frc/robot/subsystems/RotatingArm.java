// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.RotatingArmConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RotatingArm extends SubsystemBase {
  private final CANSparkMax driveMotor = new CANSparkMax(RotatingArmConfig.driveMotorPort, MotorType.kBrushless);
  private final CANSparkMax followMotor = new CANSparkMax(RotatingArmConfig.followMotorPort, MotorType.kBrushless);
  private final CANSparkMax encoderController = new CANSparkMax(RotatingArmConfig.encoderControllerPort,
      MotorType.kBrushed);

  private final ShuffleboardTab armTab;
  private final GenericEntry /* topSwitchEntry, bottomSwitchEntry, */ angleEntry, motorOutputEntry, attemptedOutputEntry, brakeEntry, speedMultiplierEntry;

  private final DoubleSolenoid brake = new DoubleSolenoid(20, PneumaticsModuleType.REVPH,
      RotatingArmConfig.brakePort[0], RotatingArmConfig.brakePort[1]);
  private final RelativeEncoder encoder;

  private boolean brakeEnabled = false;

  private double targetMotorOutput = 0;

  // private final DigitalInput topLimitSwitch;
  // private final DigitalInput bottomLimitSwitch;

  // public boolean getTopSwitch() {
  // return !topLimitSwitch.get();
  // }

  // public boolean getBottomSwitch() {
  // return !bottomLimitSwitch.get();
  // }

  public RotatingArm() {
    // topLimitSwitch = new DigitalInput(RotatingArmConfig.topLimitSwitchID);
    // bottomLimitSwitch = new DigitalInput(RotatingArmConfig.bottomLimitSwitchID);

    driveMotor.restoreFactoryDefaults();
    followMotor.restoreFactoryDefaults();
    followMotor.follow(driveMotor);

    driveMotor.setInverted(false);
    followMotor.setInverted(false);

    encoder = encoderController.getEncoder(Type.kQuadrature, 8192); // Converts rotatings to
    encoder.setPositionConversionFactor(360); // degrees
    resetEncoder();

    armTab = Shuffleboard.getTab("armTab");
    // topSwitchEntry = armTab.add("Top Switch", false).getEntry();
    // bottomSwitchEntry = armTab.add("Bottom Switch", false).getEntry();
    angleEntry = armTab.add("Arm Angle", 0).getEntry();
    motorOutputEntry = armTab.add("Motor Output", 0).getEntry();
    attemptedOutputEntry = armTab.add("Attempted Output", 0).getEntry();
    brakeEntry = armTab.add("Brake Enabled", false).getEntry();
    speedMultiplierEntry = armTab.add("Speed Multiplier", 1).getEntry();

    armTab.add(new InstantCommand(() -> setMotorIdleMode(IdleMode.kBrake)).withName("BRAKE MODE"));
    armTab.add(new InstantCommand(() -> setMotorIdleMode(IdleMode.kCoast)).withName("COAST MODE"));

    armTab.add(new InstantCommand(() -> enableBrake()).withName("Enable Brake"));
    armTab.add(new InstantCommand(() -> disableBrake()).withName("Disable Brake"));

    setMotorIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    updateShuffleboard();
    applyClawOutput();
  }

  private void applyClawOutput() {
    double motorOutput = targetMotorOutput;

    // if (getTopSwitch())
    // motorOutput = Math.min(0, motorOutput);
    // if (getBottomSwitch())
    // motorOutput = Math.max(0, motorOutput);
    attemptedOutputEntry.setDouble(targetMotorOutput);

    // if (getAngle() > RotatingArmConfig.maxArmAngleDegrees)
    //   motorOutput = Math.min(0, motorOutput);

    // if (getAngle() < RotatingArmConfig.minArmAngleDegrees)
    //   motorOutput = Math.max(0, motorOutput);

    if (!brakeEnabled && Math.abs(motorOutput) < 0.01) {
    enableBrake();
    outputToMotor(0);
    return;
    }

    if (brakeEnabled && Math.abs(motorOutput) > 0.01) {
    disableBrake();
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
    driveMotor.set(output * speedMultiplierEntry.getDouble(1));
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
    return encoder.getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation. This means the arm is pointed
   * straight down.
   */
  public void resetEncoder() {
    encoder.setPosition(0);
  }

  public void enableBrake() {
    brake.set(Value.kForward);
    outputToMotor(0);
    brakeEnabled = true;
    brakeEntry.setBoolean(true);
  }

  public void disableBrake() {
    brake.set(Value.kReverse);
    brakeEntry.setBoolean(false);
    brakeEnabled = false;
  }

  public boolean getBrakeEnabled() {
    return brakeEnabled;
  }

  public void setMotorIdleMode(IdleMode mode) {
    driveMotor.setIdleMode(mode);
    followMotor.setIdleMode(mode);
  }
}
