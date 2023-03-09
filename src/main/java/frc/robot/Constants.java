// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final static class DrivetrainConfig {
        public static final double axisThreshold = 0.25;

        public static final int leftDrivePort = 3;
        public static final int rightDrivePort = 1;
        public static final int leftFollowPort = 4;
        public static final int rightFollowPort = 2;

        public static final double driveSpeed = .7;
        public static final double turnSpeed = .4;

        // Multiplied by drive speed when in slow mode
        public static final double slowMultiplier = 0.2;

        public static final double gearRatio = 1 / 8.45; // Times motor has to rotate for wheel to rotate once
        public static final double wheelDiameterMeters = Units.inchesToMeters(6);
        public static final double rotationToDistanceConversion = (Math.PI * wheelDiameterMeters) * gearRatio; // Encoder
                                                                                                               // rotations
                                                                                                               // to
                                                                                                               // distance
                                                                                                               // moved
        public static final double drivetrainWidthMeters = Units.inchesToMeters(24); // Distance between left and right
                                                                                     // wheels in meters

        public static final double constantDriveDistSpeed = 0.2;

        // Path following PID
        // public static final double pathP = 0.017108; // <- value from SysId
        public static final double pathP = 0;
        public static final double pathI = 0;
        public static final double pathD = 0;

        // Drive distance PID
        public static final double driveDistP = 0.43;
        public static final double driveDistI = 0;
        public static final double driveDistD = 0;

        public static final double driveDistVel = 1;
        public static final double driveDistAcc = 1.5;

        public static final double turnDegP = 0.02;
        public static final double turnDegI = 0;
        public static final double turnDegD = 0;

        public static final double turnDegVel = 90;
        public static final double turnDegAcc = 120;

        // Auto turn PID
        public static final double turnP = 0.001;
        public static final double turnI = 0;
        // public static final double turnD = 0.0001;
        public static final double turnD = 0; // 0 D for initial test

        // Drivetrain characterization (Do not change, found using SysId)
        // public static final double kS = 0.13007;
        public static final double kS = 0.13007;
        // public static final double kV = 0.053671;
        public static final double kV = 1.8;
        // public static final double kA = 0.018914;
        public static final double kA = 0.018914;

        // Ramsete config (Do not change, default values from documentation)
        public static final double kB = 2.0;
        public static final double kZeta = 0.7;

        public static final double autoBalanceAngleThreshold = 5;
        public static final double autoBalanceSpeed = 0.3;
        public static final double autoBalanceProportional = 0.3;

        public static final double maxTrajectoryVel = 2;
        public static final double maxTrajectoryAcc = 1;
    }

    public final static class ShuffleboardConfig {
        public static final boolean drivetrainPrintsEnabled = true;
    }

    public final static class RotatingArmConfig {
        public static final int leftMotorPort = 5;
        public static final int rightMotorPort = 6;
        public static final int encoderControllerPort = 7;

        public static final double maxArmAngleDegrees = 100;
        public static final double minArmAngleDegrees = 3;

        public static final int[] brakePort = { 0, 5 };
        public static final double manualSpeed = 0.34;

        public static final double constantUpSpeed = 0.36;
        public static final double constantDownSpeed = 0.02;

        public static final double armLengthMeters = Units.inchesToMeters(1.0);
        public static final double armHeightMeters = Units.inchesToMeters(1.0); // height of arm mount on robot from
                                                                                // ground

        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double maxVelocity = 0.0;
        public static final double maxAcceleration = 0.0;

        public static final int topLimitSwitchID = 0;
        public static final int bottomLimitSwitchID = 1;
    }

    public static final class ClawConfig { // BRAKE 0
        public static final int leftPistonPorts[] = { 1, 2 };
        public static final int rightPistonPorts[] = { 3, 4 };

        public static final int leftMotorPort = 10;
        public static final int rightMotorPort = 11;

        public static final double motorSpeed = 0.1;

    }
}