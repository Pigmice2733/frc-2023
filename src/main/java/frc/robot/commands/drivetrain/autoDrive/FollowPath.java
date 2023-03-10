package frc.robot.commands.drivetrain.autoDrive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;

public class FollowPath extends RamseteCommand {
    /**
     * Use a RamseteController to follow a specified path.
     * Call only from autonomous path-following commands that define a trajectory
     * and a trajectory configuration.
     * 
     * @param drivetrain a drivetrain subsystem
     * @param trajectory a path-following trajectory
     */
    public FollowPath(Drivetrain drivetrain, Trajectory trajectory) {
        super(
                trajectory,
                drivetrain::getPose,
                new RamseteController(DrivetrainConfig.kB, DrivetrainConfig.kZeta),
                new SimpleMotorFeedforward(DrivetrainConfig.kS,
                        DrivetrainConfig.kV,
                        DrivetrainConfig.kA),
                drivetrain.getKinematics(),
                drivetrain::getWheelSpeeds,
                new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD), // Left
                new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI, DrivetrainConfig.pathD), // Right
                drivetrain::tankDriveVolts,
                drivetrain);
        addRequirements(drivetrain);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}