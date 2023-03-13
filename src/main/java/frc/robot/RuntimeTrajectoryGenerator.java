package frc.robot;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;

import frc.robot.Constants.DrivetrainConfig;

public class RuntimeTrajectoryGenerator {
    public static double yDistanceFromTagToConeNode = Units.inchesToMeters(22.75);
    public static double yDistanceFromTagToPickup = Units.inchesToMeters(0); // TODO: enter exact value
    public static double xDistanceFromTagToTarget = Units.inchesToMeters(24); // TODO: enter exact value
    public static double robotLength = Units.inchesToMeters(45); // TODO: enter exact value

    public static double tagRotation = 0;

    /**
     * Generates a trajectory to line up the robot to pick up or score based on an
     * AprilTag's location.
     */
    public static PathPlannerTrajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose) {
        // if we are looking at a flipped tag then we have to invert all of our
        // distances
        double tagRotated = tagPose.getRotation().getDegrees() == 0 ? -1 : 1;
        double xPos = tagPose.getX() - (robotLength / 2 + xDistanceFromTagToTarget) * tagRotated;
        double yPos = tagPose.getY();

        switch (selectedTargetLocation) { // TODO: Add support for knowing when picking up or scoring
            case Left:
                yPos += yDistanceFromTagToConeNode * tagRotated;
                break;
            case Right:
                yPos -= yDistanceFromTagToConeNode * tagRotated;
                break;
            default:
                break;
        }
        PathPoint endPoint = new PathPoint(new Translation2d(xPos, yPos),
                new Rotation2d(tagPose.getRotation().getRadians() - 3.1415));
        PathPoint currentPoint = new PathPoint(currentRobotPose.getTranslation(), currentRobotPose.getRotation());

        PathConstraints constraints = new PathConstraints(DrivetrainConfig.maxTrajectoryVel,
                DrivetrainConfig.maxTrajectoryAcc);
        PathPlannerTrajectory trajectory = PathPlanner.generatePath(constraints, List.of(currentPoint, endPoint));

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
    private static GenericEntry targetLocationEntry = RobotContainer.addToDriverTab("Target Location",
            selectedTargetLocation.toString());

    public static void setTargetType(TargetLocation targetLocation) {
        selectedTargetLocation = targetLocation;
        targetLocationEntry.setString(targetLocation.toString());
    }

    public static TargetLocation getTargetType() {
        return selectedTargetLocation;
    }
}
