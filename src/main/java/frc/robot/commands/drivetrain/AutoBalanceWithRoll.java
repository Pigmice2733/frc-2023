// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

/**
 * For use when not perfectly lined up with the charge station, but still facing
 * it. If the robot is lined up well, use AutoBalance.java
 */
public class AutoBalanceWithRoll extends CommandBase {
  private Drivetrain drivetrain;
  private boolean backwards = false;
  // private boolean rollZero = false;

  public AutoBalanceWithRoll(Drivetrain drivetrain) {
    this(drivetrain, false);
  }

  public AutoBalanceWithRoll(Drivetrain drivetrain, boolean backwards) {
    this.drivetrain = drivetrain;
    this.backwards = backwards;

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    double pitch = MathUtil.applyDeadband(drivetrain.getPitch(), 5);
    double proportionalPitch = pitch / 15.0;
    double speedPitch = proportionalPitch * DrivetrainConfig.autoBalanceProportional + 0.075 * Math.signum(pitch); // was
                                                                                                                   // 0.1,
                                                                                                                   // 0.07
    speedPitch = MathUtil.clamp(speedPitch, -0.5, 0.5);

    /*
     * double roll = -drivetrain.getRoll();
     * if (Math.abs(roll) < 1) {
     * rollZero = true;
     * }
     * 
     * double speedRoll = 0.0;
     * if (!rollZero) {
     * double proportionalRoll = roll / 15.0;
     * speedRoll = proportionalRoll * DrivetrainConfig.autoBalanceProportional +
     * 0.03 * Math.signum(roll);
     * speedRoll = MathUtil.clamp(speedRoll, -0.1, 0.1);
     * }
     */

    double roll = MathUtil.applyDeadband(-drivetrain.getRoll(), 5);
    double proportionalRoll = roll / 15.0;
    double speedRoll = proportionalRoll * DrivetrainConfig.autoBalanceProportional + 0.03 * Math.signum(roll);
    speedRoll = MathUtil.clamp(speedRoll, -0.1, 0.1);

    drivetrain.arcadeDrive(speedPitch, (backwards ? 1 : -1) * speedRoll);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
