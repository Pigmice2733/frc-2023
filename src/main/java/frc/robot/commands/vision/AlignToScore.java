// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.RuntimeTrajectoryGenerator.TargetType;
import frc.robot.commands.drivetrain.FollowPath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class AlignToScore extends CommandBase {
  private final Vision vision;
  private final Drivetrain drivetrain;
  private final Supplier<RuntimeTrajectoryGenerator.TargetType> targetType;

  private RamseteCommand pathCommand;

  public AlignToScore(Vision vision, Drivetrain drivetrain, RuntimeTrajectoryGenerator.TargetType targetType) {
    this.vision = vision;
    this.drivetrain = drivetrain;
    this.targetType = () -> targetType;
  }

  public AlignToScore(Vision vision, Drivetrain drivetrain,
      Supplier<RuntimeTrajectoryGenerator.TargetType> targetTypeSupplier) {
    this.vision = vision;
    this.drivetrain = drivetrain;
    this.targetType = targetTypeSupplier;
  }

  @Override
  public void initialize() {
    var transformToTag = vision.getTransformToTag();
    Pose2d robotPose = new Pose2d(-transformToTag.getX(), -transformToTag.getY(),
        new Rotation2d());
    Pose2d tagPose = vision.getRecentTagPose();

    if (robotPose == null || tagPose == null) {
      this.cancel();
      return;
    }

    // Sets the robot odometry to the estimated global position
    drivetrain.setOdometryPose(robotPose);

    TargetType type = targetType.get();

    System.out.println("TARGET TYPE " + type);

    Trajectory trajectory = RuntimeTrajectoryGenerator.generateLineupTrajectory(robotPose, tagPose, type);

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
    return pathCommand.isFinished();
  }
}
