// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.commands.drivetrain.autoDrive.FollowPath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class AlignToScore extends CommandBase {
  private final Vision vision;
  private final Drivetrain drivetrain;

  private Command pathCommand;

  public AlignToScore(Vision vision, Drivetrain drivetrain) {
    this.vision = vision;
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    // var transformToTag = vision.getTransformToTag();
    // Pose2d robotPose = new Pose2d(-transformToTag.getX(), -transformToTag.getY(),
    // new Rotation2d());
    Pose2d robotPose = vision.getEstimatedRobotPose();
    Pose2d tagPose = vision.getRecentTagPose();

    if (robotPose == null || tagPose == null) {
      this.cancel();
      return;
    }

    // Sets the robot odometry to the estimated global position
    drivetrain.setOdometryPose(robotPose);

    PathPlannerTrajectory trajectory = RuntimeTrajectoryGenerator.generateLineupTrajectory(robotPose, tagPose);

    pathCommand = new FollowPath(drivetrain, trajectory);
    CommandScheduler.getInstance().schedule(pathCommand);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    if (pathCommand != null)
      pathCommand.cancel();
  }

  @Override
  public boolean isFinished() {
    if (pathCommand == null)
      return true;
    return pathCommand.isFinished();
  }
}
