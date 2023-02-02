package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.DrivetrainConfig;

public final class RuntimeTrajectoryGenerator {
    public static double yDistanceFromTagToConeNode = Units.inchesToMeters(22); // TODO: enter exact value
    public static double yDistanceFromTagToPickup = Units.inchesToMeters(0); // TODO: enter exact value
    public static double xDistanceFromTagToScoreLocation = Units.inchesToMeters(0); // TODO: enter exact value
    public static double robotLength = Units.inchesToMeters(45); // TODO: enter exact value

    public static double tagRotation = 0;

    /** Generates a trajectory to line the robot to pick up or score based on an apriltag location */
    public static Trajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose, TargetType targetType) {
        //if we are looking at a flipped tag then we have to invert all of our distances
        double tagRotated = tagPose.getRotation().getDegrees() == 0 ? 1 : -1;
        double xPos = tagPose.getX() - (robotLength/2 + xDistanceFromTagToScoreLocation) * tagRotated;
        double yPos = tagPose.getY();

        switch(targetType) {
            case ConeLeft:
                yPos += yDistanceFromTagToConeNode * tagRotated;
                break;
            case ConeRight:
                yPos -= yDistanceFromTagToConeNode * tagRotated;
                break;
            case PickupLeft:
                yPos += yDistanceFromTagToPickup * tagRotated;
                break;
            case PickupRight:
                yPos -= yDistanceFromTagToPickup * tagRotated;
                break;
            default:
                break;
        }
        Pose2d targetPose = new Pose2d(xPos, yPos, tagPose.getRotation());

        TrajectoryConfig config = new TrajectoryConfig(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(List.of(currentRobotPose, targetPose), config);
        System.out.println(trajectory);
        return trajectory;
    }

    public enum TargetType {
        Cube,
        ConeLeft,
        ConeRight,
        PickupLeft,
        PickupRight
    }
}
