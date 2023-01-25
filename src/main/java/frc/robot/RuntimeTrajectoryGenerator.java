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
    public static double distanceFromTagToConeNode = Units.inchesToMeters(22);
    public static double distanceFromTagToPickup = Units.inchesToMeters(0);
    public static double robotLength = Units.inchesToMeters(31);
    public static double tagRotation = 0;

    public static Trajectory generateLineupTrajectory(Pose2d currentRobotPose, Pose2d tagPose, TargetType targetType) {

        //double xPos = tagPose.getX();
        double xPos = tagPose.getX() + robotLength/2;
        double yPos = tagPose.getY();

        switch(targetType) {
            case ConeLeft:
                yPos += distanceFromTagToConeNode;
                break;
            case ConeRight:
                yPos -= distanceFromTagToConeNode;;
                break;
            case PickupLeft:
                yPos += distanceFromTagToPickup;
                break;
            case PickupRight:
                yPos -= distanceFromTagToPickup;;
                break;
            default:
                break;
        }

        Pose2d targetPose = new Pose2d(xPos, yPos, new Rotation2d(tagRotation));

        System.out.println("Robot Pose: " + currentRobotPose);
        System.out.println("Target Pose: " + targetPose);

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
