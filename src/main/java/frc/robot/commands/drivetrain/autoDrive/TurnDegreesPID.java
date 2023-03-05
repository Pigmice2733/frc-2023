// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain.autoDrive;


import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class TurnDegreesPID extends ProfiledPIDCommand {

  private final double rotation;
  private final Drivetrain drivetrain;
  /**
   * Use profiled PID to rotate the specified angle.
   * @param drivetrain a drivetrain subsystem
   * @param rotation The degrees to rotate
   */
  public TurnDegreesPID(Drivetrain drivetrain, double rotation) {
    super(
      new ProfiledPIDController(DrivetrainConfig.turnDegP, DrivetrainConfig.turnDegI, DrivetrainConfig.turnDegD, new Constraints(DrivetrainConfig.turnDegVel, DrivetrainConfig.turnDegAcc)), 
      () -> drivetrain.getHeading().getDegrees(),
      100000, 
      (output,setpoint) -> { drivetrain.arcadeDrive(0, -output); },
      drivetrain
    );
    this.rotation = rotation;
    this.drivetrain = drivetrain;

    getController().enableContinuousInput(-180, 180);

    getController().setTolerance(2, 3);
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() { 
    super.initialize();
    double targetRotation = rotation + drivetrain.getHeading().getDegrees();
    m_goal = () -> new State(targetRotation, 0);
    getController().setGoal(m_goal.get());
    System.out.println("Set Goal: " + getController().getGoal().position);
    System.out.println("Set Goal: " + m_goal.get().position);
    SmartDashboard.putData(getController());
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("END");
    System.out.println("END");
    System.out.println("END");
  }

  @Override
  public boolean isFinished() {
    System.out.println(getController().getGoal().position);
    return getController().atGoal();
  }
}
