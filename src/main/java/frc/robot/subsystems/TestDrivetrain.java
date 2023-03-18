// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConfig;

public class TestDrivetrain extends SubsystemBase {
  private final CANSparkMax leftDrive = new CANSparkMax(DrivetrainConfig.leftDrivePort, MotorType.kBrushless);
  private final CANSparkMax rightDrive = new CANSparkMax(DrivetrainConfig.rightDrivePort, MotorType.kBrushless);

  private final CANSparkMax leftFollow = new CANSparkMax(DrivetrainConfig.leftFollowPort, MotorType.kBrushless);
  private final CANSparkMax rightFollow = new CANSparkMax(DrivetrainConfig.rightFollowPort, MotorType.kBrushless);
  
  /** Creates a new TestDrivetrain. */
  public TestDrivetrain() {
    rightDrive.restoreFactoryDefaults();
    leftDrive.restoreFactoryDefaults();
    leftFollow.restoreFactoryDefaults();
    rightFollow.restoreFactoryDefaults();

    rightDrive.setIdleMode(IdleMode.kBrake);
    leftDrive.setIdleMode(IdleMode.kBrake);
    rightFollow.setIdleMode(IdleMode.kBrake);
    leftFollow.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    double speed = 0.4;
    rightDrive.set(speed);
    leftDrive.set(speed);
    rightFollow.set(speed);
    leftFollow.set(speed);
  }
}
