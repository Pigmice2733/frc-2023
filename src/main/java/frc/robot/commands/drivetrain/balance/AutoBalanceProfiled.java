// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.balance;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class AutoBalanceProfiled extends ProfiledPIDCommand {
  private Drivetrain drivetrain;

  /** Robot must be lined up with the charge station. */
  public AutoBalanceProfiled(Drivetrain drivetrain) {
    super(
        new ProfiledPIDController(0.015, 0, 0.0,
            new Constraints(0.4, 0.4)),
        drivetrain::getPitch,
        0,
        (output, setpoint) -> {
          drivetrain.arcadeDrive(output, 0);
        },
        drivetrain);
    this.drivetrain = drivetrain;

    getController().setTolerance(3, 0.1);

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    super.execute();
  }

  @Override
  public boolean isFinished() {
    return getController().atGoal();
  }
}
