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
    public static double robotLength = Units.inchesToMeters(31); // TODO: enter exact value

    public static double tagRotation = 0;

    /** Generates a trajectory to line the robot to pick up or score based on an apriltag location */
    public static Trajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose, TargetType targetType) {
        double xPos = tagPose.getX() - robotLength/2 - xDistanceFromTagToScoreLocation;
        double yPos = tagPose.getY();

        switch(targetType) {
            case ConeLeft:
                yPos += yDistanceFromTagToConeNode;
                break;
            case ConeRight:
                yPos -= yDistanceFromTagToConeNode;;
                break;
            case PickupLeft:
                yPos += yDistanceFromTagToPickup;
                break;
            case PickupRight:
                yPos -= yDistanceFromTagToPickup;;
                break;
            default:
                break;
        }
        Pose2d targetPose = new Pose2d(xPos, yPos, tagPose.getRotation());

        TrajectoryConfig config = new TrajectoryConfig(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(List.of(currentRobotPose, targetPose), config);

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
