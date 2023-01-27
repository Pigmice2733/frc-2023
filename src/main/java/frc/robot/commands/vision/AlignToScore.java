// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/** Uses Apriltags to line up with the grid to score */
public class AlignToScore extends CommandBase {
  private final Vision vision;
  private final Drivetrain drivetrain;
  private final RuntimeTrajectoryGenerator.TargetType targetType;

  RamseteCommand pathCommand;

  public AlignToScore(Vision vision, Drivetrain drivetrain, RuntimeTrajectoryGenerator.TargetType targetType) {
    this.vision = vision;
    this.drivetrain = drivetrain;
    this.targetType = targetType;
  }

  @Override
  public void initialize() {
    Pose2d robotPose = vision.getGlobalRobotPosition();    
    Pose2d tagPose = vision.getTagPosition();

    System.out.println(tagPose);
    System.out.println(robotPose);

    if (robotPose == null || tagPose == null) {
      cancel();
      return;
    }

    // Sets the robot odometry to the estimated gloabl position
    drivetrain.setOdometryPose(robotPose);

    Trajectory trajectory = RuntimeTrajectoryGenerator.generateLineupTrajectory(robotPose, tagPose, targetType);
    
    pathCommand = new FollowPath(drivetrain, trajectory);
    CommandScheduler.getInstance().schedule(pathCommand);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {
    if (pathCommand != null)
      pathCommand.cancel();
  }

  @Override
  public boolean isFinished() {
    return pathCommand.isFinished();
  }
}
