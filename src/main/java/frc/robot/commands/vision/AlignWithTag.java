// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;


public class AlignWithTag extends CommandBase {

  private final PIDController angularController;
  private final PIDController linearController;

  private final Drivetrain drivetrain;
  private final Vision vision;

  /** Creates a new AlignWithTag. */
  public AlignWithTag(Drivetrain drivetrain, Vision vision, PIDController rotateController, PIDController linearController) {
    System.out.println("Command Started");
    this.drivetrain = drivetrain;
    this.vision = vision;

    this.angularController = rotateController;
    this.angularController.setTolerance(0);

    this.linearController = linearController;

    drivetrain.resetOdometry();

    PhotonTrackedTarget target = vision.getBestTarget();

    if (target == null) {
      this.cancel();
      return;
    }

    System.out.println(target.getYaw());
    angularController.setSetpoint(target.getYaw());

    System.out.println();
    linearController.setSetpoint(1);

    addRequirements(drivetrain, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // PhotonTrackedTarget target = vision.getTarget();

    // double currentYaw = target.getYaw();
    // double currentDistance = PhotonUtils.calculateDistanceToTargetMeters(0, 0, 0, Units.degreesToRadians(target.getPitch()));

    // System.out.println(currentYaw);

    //double forwardSpeed = linearController.calculate(currentDistance);
    double rotationSpeed = angularController.calculate(-drivetrain.getHeading().getDegrees());
    SmartDashboard.putNumber("RotateSpeed", rotationSpeed);
    drivetrain.arcadeDrive(0, rotationSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Command Ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
