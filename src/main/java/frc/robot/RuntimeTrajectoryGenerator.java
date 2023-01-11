package frc.robot;

import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.DrivetrainConfig;

public final class RuntimeTrajectoryGenerator {
    public static double distanceFromTagToConeNode = Units.feetToMeters(0);
    public static double distanceFromTagToPickup = Units.feetToMeters(0);
    public static double robotLength = 0;
    public static double tagRotation = 0;

    public static Trajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose, TargetType targetType) {
        double xPos = tagPose.getX() + robotLength/2;
        double yPos = tagPose.getY();

        switch(targetType) {
            case ConeLeft:
                xPos -= distanceFromTagToConeNode;
                break;
            case ConeRight:
                xPos += distanceFromTagToConeNode;;
                break;
            case PickupLeft:
                xPos -= distanceFromTagToPickup;
                break;
            case PickupRight:
                xPos += distanceFromTagToPickup;;
                break;
            default:
                break;
        }

        Pose2d targetPose = new Pose2d(xPos, yPos, new Rotation2d(tagRotation));

        TrajectoryConfig config = new TrajectoryConfig(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc);

        return TrajectoryGenerator.generateTrajectory(List.of(currentRobotPose, targetPose), config);
    }

    public enum TargetType {
        Cube,
        ConeLeft,
        ConeRight,
        PickupLeft,
        PickupRight
    }
}
