// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.ShuffleboardConfig;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class Drivetrain extends SubsystemBase {
  GenericEntry xPosEntry;

  private final GenericEntry yPosEntry, headingEntry, leftOutputEntry, rightOutputEntry;

  private final CANSparkMax leftDrive = new CANSparkMax(DrivetrainConfig.leftDrivePort, MotorType.kBrushless);
  private final CANSparkMax rightDrive = new CANSparkMax(DrivetrainConfig.rightDrivePort, MotorType.kBrushless);

  private final CANSparkMax leftFollow = new CANSparkMax(DrivetrainConfig.leftFollowPort, MotorType.kBrushless);
  private final CANSparkMax rightFollow = new CANSparkMax(DrivetrainConfig.rightFollowPort, MotorType.kBrushless);

  private final AHRS gyro = new AHRS();

  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DrivetrainConfig.drivetrainWidthMeters);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0, new Pose2d());

  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DrivetrainConfig.kS, DrivetrainConfig.kV, DrivetrainConfig.kA);

  private Pose2d pose = new Pose2d();

  private boolean slowEnabled = false;
  private boolean backwards = false;
  public double outputFactor = 1;

  public Drivetrain() {
    rightDrive.restoreFactoryDefaults();
    leftDrive.restoreFactoryDefaults();
    leftFollow.restoreFactoryDefaults();
    rightFollow.restoreFactoryDefaults();

    enableBrakeMode();

    leftFollow.follow(leftDrive);
    rightFollow.follow(rightDrive);
    
    leftDrive.setInverted(false);
    rightDrive.setInverted(true);

    leftDrive.getEncoder().setPositionConversionFactor(DrivetrainConfig.rotationToDistanceConversion);
    rightDrive.getEncoder().setPositionConversionFactor(DrivetrainConfig.rotationToDistanceConversion);

    ShuffleboardTab driveTab = Shuffleboard.getTab("Drivetrain");

    xPosEntry = driveTab.add("X", 0.0).getEntry();
    yPosEntry = driveTab.add("Y", 0.0).getEntry();
    headingEntry = driveTab.add("Heading", 0.0).getEntry();

    leftOutputEntry = driveTab.add("Left Output", 0).getEntry();
    rightOutputEntry = driveTab.add("Right Output", 0).getEntry();

    resetOdometry();
  }

  public void periodic() {
    SmartDashboard.putNumber("Roll", gyro.getRoll());
    updateOdometry();
  }

  /**
   * Sets motor output factor to a slowMultiplier if slowmode is enabled or 1 if slowmode is disabled.
   * @param slowEnabled whether or not slowmode should be enabled
   */
  public void setSlow(boolean slowEnabled) { 
    this.slowEnabled = slowEnabled; 
    outputFactor = slowEnabled ? DrivetrainConfig.slowMultiplier : 1; 
  }
  public void enableSlow() { setSlow(true); }
  public void disableSlow() { setSlow(false); }
  public void toggleSlow() { setSlow(!this.slowEnabled); }
  
  public void setBackwards(boolean backwards) {this.backwards = backwards;}   
  public void enableBackwards() { setBackwards(true); }
  public void disableBackwards() { setBackwards(false); }
  public void toggleBackwards() { setBackwards(!this.backwards); }

  /** Updates the odometry pose with the heading and position measurements. */
  private void updateOdometry() {
    pose = odometry.update(getHeadingRadians(), leftDrive.getEncoder().getPosition(), rightDrive.getEncoder().getPosition());

    if (ShuffleboardConfig.drivetrainPrintsEnabled) {
      xPosEntry.setDouble(pose.getX());
      yPosEntry.setDouble(pose.getY());
      headingEntry.setDouble(getHeadingRadians().getDegrees());
    }
  }

  /** Returns the robot's current yaw as a Rotation2d object (in radians). */
  public Rotation2d getHeadingRadians() {
    return new Rotation2d(-gyro.getAngle() * (Math.PI / 180));
  }

  /** Returns the robot's current pitch in degrees. */
  public double getPitch() {
    return gyro.getPitch();
  }

  /** Returns the robot's current roll in degrees. */
  public double getRoll() {
    return gyro.getRoll();
  }

  /** Returns a DifferentialDriveWheelSpeeds object from the encoder velocities. */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    double left = leftDrive.getEncoder().getVelocity();
    double right = rightDrive.getEncoder().getVelocity();

    return new DifferentialDriveWheelSpeeds(left, right);
  }

 /** Returns the average distance moved by left and right wheels since last reset. */
  public double getAverageDistance() {
    double distance = (leftDrive.getEncoder().getPosition() + rightDrive.getEncoder().getPosition()) / 2;
    
    return (Math.abs(distance) < 0.1) ? 0.1 : distance;
  }

  /** Returns the feedforward object used by the drivetrain. */
  public SimpleMotorFeedforward getFeedForward() {
    return feedforward;
  }

  /** Returns the DifferentialDriveKinematics object used by the drivetrain. */
  public DifferentialDriveKinematics getKinematics() {
    return kinematics;  
  }

  /** Returns the robot's current pose. */
  public Pose2d getPose() {
    return pose;
  }

  /** Zeros odometry, gyro, and drive encoders. */
  public void resetOdometry() {
    gyro.reset();
    gyro.setAngleAdjustment(0);
    
    odometry.resetPosition(new Rotation2d(), 0.0, 0.0, new Pose2d());

    leftDrive.getEncoder().setPosition(0);
    rightDrive.getEncoder().setPosition(0);
  }

  /** Sets odometry to a specific Pose2d. */
  public void setOdometryPose(Pose2d newPose){
    gyro.reset();
    gyro.setAngleAdjustment(newPose.getRotation().getDegrees());
    odometry.resetPosition(newPose.getRotation(), 0, 0, newPose);

    leftDrive.getEncoder().setPosition(0);
    rightDrive.getEncoder().setPosition(0);
  }

  /**
   * Drives the robot with given speeds for left and right wheels.
   * 
   * @param left  speed of left wheels
   * @param right speed of right wheels
   */
  public void tankDrive(double left, double right) {
    updateOutputs(left, right);
  }

  /**
   * Drives the robot with given voltages for left and right wheels.
   * Input values are clamped between -12 and 12 because the motor cannot handle voltages more than 12V.
   * 
   * @param left  voltage for left wheels
   * @param right voltage for right wheels
   */
  public void tankDriveVolts(double left, double right) {
    updateOutputs(MathUtil.clamp(left/12.0, -1, 1), MathUtil.clamp(right/12.0, -1, 1)); // Divides by 12 to scale possible inputs between 0 and 1 (12 in max volts)
  }

  /**
   * Drives the robot with given directional and rotational speeds.
   * 
   * @param forward speed in robot's current direction
   * @param turn turn speed (clockwise is positive)
   */
  public void arcadeDrive(double forward, double turn) {
    double left = forward + turn;
    double right = forward - turn;

    updateOutputs(left, right);
  }

  public void stop() {
    updateOutputs(0, 0);
  }

  public void updateOutputs(double left, double right) {
    // Clamp outputs FOR TESTING (to make sure drivetrain does not go crazy while testing auto commands)
    // WILL BE REMOVED FOR COMP
    // TODO (see above)

    if(backwards){
      left *= -1;
      right *= -1;
    }

    double maxSpeed = 1;
    left = Math.max(Math.min(maxSpeed, left), -maxSpeed);
    right = Math.max(Math.min(maxSpeed, right), -maxSpeed);

    leftDrive.set(left * outputFactor);
    rightDrive.set(right * outputFactor);
    
    if (ShuffleboardConfig.drivetrainPrintsEnabled) {
      leftOutputEntry.setDouble(left);
      rightOutputEntry.setDouble(right);
    }
  }

  public void enableBrakeMode() {
    leftDrive.setIdleMode(IdleMode.kBrake);
    rightDrive.setIdleMode(IdleMode.kBrake);
    leftFollow.setIdleMode(IdleMode.kBrake);
    rightFollow.setIdleMode(IdleMode.kBrake);
  }
  
  public void enableCoastMode() {
    leftDrive.setIdleMode(IdleMode.kCoast);
    rightDrive.setIdleMode(IdleMode.kCoast);
    leftFollow.setIdleMode(IdleMode.kCoast);
    rightFollow.setIdleMode(IdleMode.kCoast);
  }
}
