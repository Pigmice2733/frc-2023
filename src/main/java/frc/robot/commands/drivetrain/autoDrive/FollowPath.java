package frc.robot.commands.drivetrain.autoDrive;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;
import com.pathplanner.lib.commands.PPRamseteCommand;

import java.util.HashMap;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class FollowPath extends SequentialCommandGroup {
    /**
     * Use a RamseteController to follow a specified path.
     * Call only from autonomous path-following commands that define a trajectory
     * and a trajectory configuration.
     * 
     * @param drivetrain a drivetrain subsystem
     * @param trajectory a path-following trajectory
     */
    public FollowPath(Drivetrain drivetrain, PathPlannerTrajectory trajectory) {
        addCommands(
                drivetrain.setOdometryPoseCommand(trajectory.getInitialPose()),
                new PPRamseteCommand(
                        trajectory,
                        drivetrain::getPose,
                        new RamseteController(),
                        new SimpleMotorFeedforward(DrivetrainConfig.kS, DrivetrainConfig.kV),
                        drivetrain.getKinematics(),
                        drivetrain::getWheelSpeeds,
                        new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD),
                        new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD),
                        drivetrain::driveVoltages,
                        true,
                        drivetrain));
        addRequirements(drivetrain);
    }
    public FollowPath(Drivetrain drivetrain, PathPlannerTrajectory trajectory, HashMap<String, Command> eventMap) {
        addCommands(
            drivetrain.setOdometryPoseCommand(trajectory.getInitialPose()),
            new FollowPathWithEvents(
                new PPRamseteCommand(
                    trajectory,
                    drivetrain::getPose,
                    new RamseteController(),
                    new SimpleMotorFeedforward(DrivetrainConfig.kS, DrivetrainConfig.kV), 
                    drivetrain.getKinematics(), 
                    drivetrain::getWheelSpeeds, 
                    new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD),
                    new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD),
                    drivetrain::driveVoltages, 
                    true, 
                    drivetrain), 
                trajectory.getMarkers(), 
                eventMap)
        );
        addRequirements(drivetrain);
    }

    /**
     * Use a RamseteController to follow a specified path.
     * Call only from autonomous path-following commands that define a trajectory
     * and a trajectory configuration.
     * 
     * @param drivetrain a drivetrain subsystem
     * @param pathName   the name of a premade path to follow
     */
    public FollowPath(Drivetrain drivetrain, String pathName) {
        this(
                drivetrain,
                PathPlanner.loadPath(pathName,
                        new PathConstraints(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc),
                        true));
    }

    public FollowPath(Drivetrain drivetrain, String pathName, HashMap<String, Command> eventMap) {
        this(
            drivetrain, 
            PathPlanner.loadPath(pathName, new PathConstraints(DrivetrainConfig.maxTrajectoryVel, DrivetrainConfig.maxTrajectoryAcc)),
            eventMap
        );
    }
}