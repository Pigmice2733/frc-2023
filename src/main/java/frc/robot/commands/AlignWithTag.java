// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;


public class AlignWithTag extends CommandBase {

  private final PIDController angularController = new PIDController(0, 0, 0);
  private final PIDController linearController = new PIDController(0, 0, 0);

  private final Drivetrain drivetrain;
  private final Vision vision;

  /** Creates a new AlignWithTag. */
  public AlignWithTag(Drivetrain drivetrain, Vision vision) {
    this.drivetrain = drivetrain;
    this.vision = vision;

    angularController.setSetpoint(0);
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
    PhotonTrackedTarget target = vision.getTarget();

    double currentYaw = target.getYaw();
    double currentDistance = PhotonUtils.calculateDistanceToTargetMeters(0, 0, 0, Units.degreesToRadians(target.getPitch()));

    double forward = linearController.calculate(currentDistance);
    double rotationSpeed = angularController.calculate(currentYaw);
    drivetrain.arcadeDrive(forward, rotationSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
