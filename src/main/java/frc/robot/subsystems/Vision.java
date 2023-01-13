// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.nio.file.Path;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.RobotPoseEstimator;
//import edu.wpi.first.apri;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  private final PhotonCamera camera = new PhotonCamera("photonvision");
  //private final RobotPoseEstimator poseEstimator = new RobotPoseEstimator();


  //private final RobotPose poseEstimator;

  /** Creates a new Vision. */
  public Vision() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public PhotonTrackedTarget getTarget() {
    var result = camera.getLatestResult();
    //List<PhotonTrackedTarget> targets = result.getTargets();
    return result.getBestTarget();
  }
}
