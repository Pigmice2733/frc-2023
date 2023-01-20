// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.RobotPoseEstimator;
//import edu.wpi.first.apri;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.Drivetrain;

public class Vision extends SubsystemBase {

  private final PhotonCamera camera = new PhotonCamera("OV5647");
  //private final RobotPoseEstimator poseEstimator = new RobotPoseEstimator();
  AprilTagFieldLayout layout;
  private final PhotonPoseEstimator poseEstimator = new PhotonPoseEstimator(layout, null, camera, null);
  Drivetrain drivetrain;

  //private final RobotPose poseEstimator;

  /** Creates a new Vision. */
  public Vision() {
    
  }

  @Override
  public void periodic() {
    if (!camera.getLatestResult().hasTargets())
      return;

      var target = camera.getLatestResult().getBestTarget();

    SmartDashboard.putNumber("Yaw", target.getYaw());
    SmartDashboard.putNumber("Pitch", target.getPitch());
    SmartDashboard.putNumber("Skew", target.getSkew());
    double currentDistance = PhotonUtils.calculateDistanceToTargetMeters(Units.inchesToMeters(9 + 3.0/8.0), Units.inchesToMeters(27 + 5.0/8.0), Units.degreesToRadians(33), Units.degreesToRadians(target.getPitch()));
    SmartDashboard.putNumber("Distance", currentDistance);
    SmartDashboard.putNumber("X", getGlobalPosition(drivetrain.getPose()).getX());
    SmartDashboard.putNumber("Y", getGlobalPosition(drivetrain.getPose()).getY());

  }

  public PhotonTrackedTarget getTarget() {
    
    var result = camera.getLatestResult();
    if (result == null)
      return null;

    return result.getBestTarget();
  }

  //assumes the robot is on the ground, and not on a charging station (or otherwise in the air somehow)
  public Pose2d getGlobalPosition(Pose2d referencePose){

    poseEstimator.setReferencePose(referencePose);
    EstimatedRobotPose timedPose = poseEstimator.update().get();

    return timedPose.estimatedPose.toPose2d();

  }
}
