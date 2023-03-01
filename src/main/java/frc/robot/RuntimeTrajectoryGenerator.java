package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DrivetrainConfig;

public class RuntimeTrajectoryGenerator {
    public static double yDistanceFromTagToConeNode = Units.inchesToMeters(22); // TODO: enter exact value
    public static double yDistanceFromTagToPickup = Units.inchesToMeters(0); // TODO: enter exact value
    public static double xDistanceFromTagToTarget = Units.inchesToMeters(24); // TODO: enter exact value
    public static double robotLength = Units.inchesToMeters(45); // TODO: enter exact value

    public static double tagRotation = 0;

    /** Generates a trajectory to line up the robot to pick up or score based on an AprilTag's location. */
    public static Trajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose) {
        // if we are looking at a flipped tag then we have to invert all of our distances
        double tagRotated = tagPose.getRotation().getDegrees() == 0 ? -1 : 1;
        double xPos = tagPose.getX() - (robotLength/2 + xDistanceFromTagToTarget) * tagRotated;
        double yPos = tagPose.getY();

        switch(selectedTargetLocation) { // TODO: Add support for knowing when picking up or scoring
            case Left:
                yPos += yDistanceFromTagToConeNode * tagRotated;
                break;
            case Right:
                yPos -= yDistanceFromTagToConeNode * tagRotated;
                break;
            default:
                break;
        }
        Pose2d targetPose = new Pose2d(xPos, yPos, new Rotation2d(tagPose.getRotation().getRadians()-3.1415));

        TrajectoryConfig config = new TrajectoryConfig(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(List.of(currentRobotPose, targetPose), config);
        
        // System.out.println("Current Pose "+ currentRobotPose);
        // System.out.println("Target Pose "+ targetPose);
        // System.out.println(trajectory);

        return trajectory;
    }

    public enum TargetLocation {
        Center,
        Left,
        Right
    }

    private static TargetLocation selectedTargetLocation = TargetLocation.Center;
    private static GenericEntry targetLocationEntry = RobotContainer.addToDriverTab("Target Location", selectedTargetLocation.toString());

    public static void setTargetType(TargetLocation targetLocation) { selectedTargetLocation = targetLocation; 
        targetLocationEntry.setString(targetLocation.toString()); }

    public static TargetLocation getTargetType() { return selectedTargetLocation; }
}
