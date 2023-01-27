// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.photonvision.RobotPoseEstimator;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.RobotPoseEstimator;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  private final PhotonCamera camera = new PhotonCamera("OV5647");

  AprilTagFieldLayout layout = new AprilTagFieldLayout(List.of(new AprilTag(2, new Pose3d(new Translation3d(0, 0, Units.inchesToMeters(26.2)), new Rotation3d(0, 0, Math.toRadians(180))))), 50, 50);
  private final RobotPoseEstimator poseEstimator = new RobotPoseEstimator(layout, RobotPoseEstimator.PoseStrategy.AVERAGE_BEST_TARGETS, Arrays.asList(new Pair(camera, new Transform3d(new Translation3d(0, 0, Units.inchesToMeters(9)), new Rotation3d(0, Math.toRadians(23), 0)))));
  
  public Vision() {
  }

  @Override
  public void periodic() {
    updateDrivetrainPose();
  }

  private void updateDrivetrainPose() {
    if (!camera.getLatestResult().hasTargets())
      return;

    var target = camera.getLatestResult().getBestTarget();

    Pose2d currentRobotPose = getGlobalRobotPosition();

    SmartDashboard.putNumber("TargetYaw", target.getYaw());

    if (currentRobotPose == null) {
      return;
    }

    //SmartDashboard.putNumber("RobotYaw", currentRobotPose.getRotation().getDegrees());
    SmartDashboard.putNumber("X", currentRobotPose.getX());
    SmartDashboard.putNumber("Y", currentRobotPose.getY());
  }

  public PhotonTrackedTarget getBestTarget() {
    
    var result = camera.getLatestResult();
    if (result == null)
      return null;

    return result.getBestTarget();
  }

  /** Returns the position of the robot on the feild based on the best target. Assumes the robot is level to the ground */
  // public Pose2d getGlobalRobotPosition(){

  //   // poseEstimator.setReferencePose(drivetrain.getPose());
  //   Optional<Pair<Pose3d, Double>> optionalPose = poseEstimator.update();

  //   if (optionalPose.isEmpty()) return null;

  //   Pair<Pose3d, Double> timedPose = optionalPose.get();

  //   //drivetrain.resetOdometry(timedPose.getFirst().toPose2d());
  //   //return drivetrain.getPose();
  //   return optionalPose.get().getFirst().toPose2d();
  // }

  /** Returns the position of the robot on the feild based on the best target. Assumes the robot is level to the ground */
  public Pose2d getGlobalRobotPosition() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (!result.hasTargets())
      return null;

    PhotonTrackedTarget target = result.getBestTarget();

    var tagPose = layout.getTagPose(target.getFiducialId());
    if (tagPose.isEmpty()) 
      return null;

    Pose2d robotPose = tagPose.get().transformBy(target.getBestCameraToTarget()).toPose2d();
    return robotPose;
  }

  /** Returns a transform to bring the current robot position to the best target */
  public Translation2d getTransformToTag() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (!result.hasTargets())
      return null;

    PhotonTrackedTarget target = result.getBestTarget();
    var toTag3d = target.getBestCameraToTarget();

    return new Translation2d(toTag3d.getX(), toTag3d.getY());
  }

  /** Returns the position and rotation of the nearest apriltag */
  public Pose2d getTagPosition() {
    if (!camera.getLatestResult().hasTargets())
      return null;

    var target = camera.getLatestResult().getBestTarget();

    var pose = layout.getTagPose(target.getFiducialId());
    if (pose.isEmpty())
      return null;

    return pose.get().toPose2d();
  }
}
