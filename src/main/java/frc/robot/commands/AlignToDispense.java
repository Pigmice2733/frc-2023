// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.photonvision.PhotonTargetSortMode;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class AlignToDispense extends CommandBase {
  Vision vision;
  Drivetrain drivetrain;

  public AlignToDispense(Vision vision, Drivetrain drivetrain) {
    this.vision = vision;
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    Pose3d robotPose = vision.getGlobalPosition(drivetrain.getPose());
    Pose2d tagPose = vision.getTagPosition();

    if (robotPose == null || tagPose == null) {
      System.out.println("NO TAG FOUND");
      return;
    }

    Trajectory trajectory = RuntimeTrajectoryGenerator.generateLineupTrajectory(robotPose.toPose2d(), tagPose, RuntimeTrajectoryGenerator.TargetType.Cube);

    CommandScheduler.getInstance().schedule(new FollowPath(drivetrain, trajectory));
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
