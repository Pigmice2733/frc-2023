// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  private final PhotonCamera camera = new PhotonCamera("OV5647");

  private AprilTagFieldLayout layout;

  Drivetrain drivetrain;

  private PhotonPipelineResult camResult;

  private final PhotonPoseEstimator poseEstimator;

  private final GenericEntry robotXEntry, robotYEntry, robotYawEntry, tagXEntry, tagYEntry, tagYawEntry;

  public Vision() {
    try {
      this.layout = AprilTagFields.k2023ChargedUp.loadAprilTagLayoutField();
      System.out.println("LOADED FIELD WITH " + this.layout.getTags().size() + " TAGS.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    poseEstimator = new PhotonPoseEstimator(
        layout,
        PhotonPoseEstimator.PoseStrategy.AVERAGE_BEST_TARGETS,
        camera,
        new Transform3d(new Translation3d(0, 0, Units.inchesToMeters(9)), new Rotation3d(0, Math.toRadians(23), 0)));

    ShuffleboardTab visionTab = Shuffleboard.getTab("Vision");
    robotXEntry = visionTab.add("Robot X", 0).withPosition(1, 0).getEntry();
    robotYEntry = visionTab.add("Robot Y", 0).withPosition(2, 0).getEntry();
    robotYawEntry = visionTab.add("Robot Yaw", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", -180, "max", 180)).withPosition(0, 0).getEntry();

    tagXEntry = visionTab.add("Tag X", 0).withPosition(1, 1).getEntry();
    tagYEntry = visionTab.add("Tag Y", 0).withPosition(2, 1).getEntry();
    tagYawEntry = visionTab.add("Tag Yaw", 0).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", -180, "max", 180)).withPosition(0, 1).getEntry();
  }

  private Pose2d estimatedRobotPose;
  private Pose2d recentTagPose;

  public Pose2d getEstimatedRobotPose() {
    return estimatedRobotPose;
  }

  public Pose2d getRecentTagPose() {
    return recentTagPose;
  }

  @Override
  public void periodic() {
    camResult = camera.getLatestResult();

    calculateGlobalRobotPosition();
    updateShuffleboard();
  }

  private void updateShuffleboard() {
    if (estimatedRobotPose == null || recentTagPose == null || Double.isNaN(estimatedRobotPose.getX())) {
      return;
    }

    robotYawEntry.setDouble(estimatedRobotPose.getRotation().getDegrees());
    robotXEntry.setDouble(estimatedRobotPose.getX());
    robotYEntry.setDouble(estimatedRobotPose.getY());

    tagYawEntry.setDouble(recentTagPose.getRotation().getDegrees());
    tagXEntry.setDouble(recentTagPose.getX());
    tagYEntry.setDouble(recentTagPose.getY());
  }

  /** Returns the current best target or null if no targets are found. */
  public PhotonTrackedTarget getBestTarget() {
    if (camResult == null)
      return null;

    return camResult.getBestTarget();
  }

  public double getRotationToTag() {
    return camResult.getBestTarget().getYaw();
  }

  /** Sets the estimated robot pose and recent target pose. */
  private void calculateGlobalRobotPosition() {
    if (!camResult.hasTargets()) {
      return;
    }

    var poseEstimation = poseEstimator.update();
    if (poseEstimation.isEmpty())
      return;

    Pose2d currentRobotPose = poseEstimation.get().estimatedPose.toPose2d();
    Pose2d tagPosition = getTagPosition();

    if (currentRobotPose == null || tagPosition == null) {
      return;
    }

    // Stops code from occasionally crashing when tag pose is NaN
    if (Double.isNaN(currentRobotPose.getX()))
      return;

    estimatedRobotPose = currentRobotPose;
    recentTagPose = tagPosition;
  }

  /**
   * Returns a transform to bring the current robot position to the best target.
   */
  public Translation2d getTranslationToTag() {
    if (!camResult.hasTargets())
      return null;

    PhotonTrackedTarget target = camResult.getBestTarget();
    Transform3d toTag = target.getBestCameraToTarget();

    return toTag.getTranslation().toTranslation2d();
  }

  /** Returns the position and rotation of the nearest AprilTag. */
  public Pose2d getTagPosition() {
    if (!camResult.hasTargets())
      return null;

    PhotonTrackedTarget target = camResult.getBestTarget();

    Optional<Pose3d> pose = layout.getTagPose(target.getFiducialId());
    if (pose.isEmpty())
      return null;

    return pose.get().toPose2d();
  }
}
