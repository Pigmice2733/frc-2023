// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RotatingArmConfig;
import frc.robot.commands.rotatingArm.RotateArmToAngle;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;

public class RotatingArm extends SubsystemBase {
  private final CANSparkMax leftMotor = new CANSparkMax(RotatingArmConfig.leftMotorPort, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(RotatingArmConfig.rightMotorPort, MotorType.kBrushless);

  private final CANSparkMax encoderController = new CANSparkMax(RotatingArmConfig.encoderControllerPort,
      MotorType.kBrushed);

  private final ShuffleboardTab armTab;
  private final GenericEntry angleEntry, targetOutputEntry, motorOutputEntry,
      brakeEntry, setpointEntry, goalEntry, motorAmpEntry, motorTempEntry;

  // private final DoubleSolenoid brake = new DoubleSolenoid(20,
  // PneumaticsModuleType.REVPH,
  // RotatingArmConfig.brakePort[0], RotatingArmConfig.brakePort[1]);
  private final RelativeEncoder encoder;

  private boolean brakeEnabled = false;

  ProfiledPIDController armController = new ProfiledPIDController(RotatingArmConfig.kP, RotatingArmConfig.kI,
      RotatingArmConfig.kD,
      new TrapezoidProfile.Constraints(RotatingArmConfig.maxVelocity, RotatingArmConfig.maxAcceleration));

  public void changeSetpoint(double change) {
    setGoal(armController.getGoal().position + change);
  }

  public void setGoal(double goal) {
    armController.setGoal(goal);
    if (goalEntry != null) goalEntry.setDouble(goal);
  }

  public void setGoal(ArmHeight height) {
    setGoal(RotateArmToAngle.scoreHeightToAngle(height));
  }

  public void resetSetpointAndGoal() {
    setGoal(0);
    armController.reset(0);
  }

  public Command setSetpointCommand(double setpoint) {
    return new InstantCommand(() -> setGoal(setpoint));
  }

  public Command setSetpointCommand(ArmHeight height) {
    return new InstantCommand(() -> setGoal(height));
  }

  public boolean atGoal() {
    return armController.atGoal();
  }

  public RotatingArm() {
    armController.setTolerance(1, 3);
    resetSetpointAndGoal();

    leftMotor.restoreFactoryDefaults();
    rightMotor.restoreFactoryDefaults();

    leftMotor.setInverted(false);
    rightMotor.setInverted(false);

    leftMotor.setSmartCurrentLimit(50);
    rightMotor.setSmartCurrentLimit(50);

    setMotorIdleMode(IdleMode.kCoast);

    encoder = encoderController.getEncoder(Type.kQuadrature, 8192); // Converts rotatings to
    encoder.setPositionConversionFactor(360); // degrees
    resetEncoder();

    armTab = Shuffleboard.getTab("armTab");

    angleEntry = armTab.add("Arm Angle", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 180)).withPosition(0, 2).getEntry();
    setpointEntry = armTab.add("Setpoint", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 180)).withPosition(1, 2).withSize(1, 2).withSize(1, 1).getEntry();
    goalEntry = armTab.add("Goal", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 180)).withPosition(2, 2).withSize(1, 2).withSize(1, 1).getEntry();
    

    motorOutputEntry = armTab.add("Motor Output", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 1)).withPosition(1, 0).getEntry();
    targetOutputEntry = armTab.add("Target Output", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 1)).withPosition(0, 0).getEntry();

    motorTempEntry = armTab.add("Avg Temp", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 100)).withPosition(0, 4).getEntry();
    motorAmpEntry = armTab.add("Avg Amp", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 60)).withPosition(1, 4).getEntry();

    armTab.add(new InstantCommand(() -> setMotorIdleMode(IdleMode.kBrake)).withName("BRAKE MODE")).withPosition(5, 0);
    armTab.add(new InstantCommand(() -> setMotorIdleMode(IdleMode.kCoast)).withName("COAST MODE")).withPosition(4, 0);

    armTab.add(new InstantCommand(() -> enableBrake()).withName("Enable Brake")).withPosition(4, 2);
    armTab.add(new InstantCommand(() -> disableBrake()).withName("Disable Brake")).withPosition(5, 2);
    brakeEntry = armTab.add("Brake Enabled", brakeEnabled).withPosition(6, 2).getEntry();
  }

  @Override
  public void periodic() {
    updateShuffleboard();
    updateController();

    SmartDashboard.putNumber("Amps", leftMotor.getOutputCurrent());
    SmartDashboard.putNumber("Temp", leftMotor.getMotorTemperature());
  }

  /**
   * Must always call outputToMotor ONCE and be called periodicly for the linear
   * filter to work correctly
   */
  private void updateController() {
    double controllerOutput = armController.calculate(getAngle());

    // if (getAngle() > RotatingArmConfig.maxArmAngleDegrees) // Upper software stop
    // motorOutput = Math.min(0, motorOutput);
    // if (getAngle() < RotatingArmConfig.minArmAngleDegrees) // Lower software stop
    // motorOutput = Math.max(0, motorOutput);

    if (armController.atSetpoint() && !brakeEnabled)
      enableBrake();
    else if (!armController.atSetpoint() && brakeEnabled)
      disableBrake();

    if (brakeEnabled)
      controllerOutput = 0;

    double gravityCompensation = (Math.sin(getAngle() * (Math.PI / 180)) / 2) * RotatingArmConfig.armGravCompensation;

    controllerOutput += gravityCompensation;

    outputToMotor(controllerOutput);
  }

  private void outputToMotor(double output) {
    targetOutputEntry.setDouble(output);

    output = Math.max(0.001, output); // 0.001 to make motors never completely stop when arm is lowering

    motorOutputEntry.setDouble(output);

    leftMotor.set(-output);
    rightMotor.set(output);
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
    setpointEntry.setDouble(armController.getSetpoint().position);
    motorTempEntry.setDouble((leftMotor.getMotorTemperature()+rightMotor.getMotorTemperature()) / 2d);
    motorAmpEntry.setDouble((leftMotor.getOutputCurrent()+rightMotor.getOutputCurrent()) / 2d);
  }

  /** Get the current angle of the arm. */
  public double getAngle() {
    return -encoder.getPosition();
  }

  /**
   * Reset the encoder to be at zero rotation. This means the arm is pointed
   * straight down.
   */
  public void resetEncoder() {
    encoder.setPosition(0);
  }

  public void enableBrake() {
    // brake.set(Value.kForward);
    outputToMotor(0);
    brakeEnabled = true;
    brakeEntry.setBoolean(true);
  }

  public void disableBrake() {
    // brake.set(Value.kReverse);
    brakeEntry.setBoolean(false);
    brakeEnabled = false;
  }

  public boolean getBrakeEnabled() {
    return brakeEnabled;
  }

  public void setMotorIdleMode(IdleMode mode) {
    leftMotor.setIdleMode(mode);
    rightMotor.setIdleMode(mode);
  }

  // public void setSetpointToCurrentAngle() {
  //   setSetpoint(getAngle());
  // }
}
