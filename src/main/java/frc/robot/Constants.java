// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final static class DrivetrainConfig {
        public static final double axisThreshold = 0.1;

        public static final int leftDrivePort = 1;
        public static final int rightDrivePort = 4;
        public static final int leftFollowPort = 2;
        public static final int rightFollowPort = 3;
    
        public static final double driveSpeed = .2;
        public static final double turnSpeed = .2;
    
        // Multiplied by drive speed when in slow mode
        public static final double slowMultiplier = 0.25;
    
        public static final double gearRatio = 1 / 8.45; // Times motor has to rotate for wheel to rotate once
        public static final double wheelDiameterMeters = Units.inchesToMeters(6);
        public static final double rotationToDistanceConversion = (Math.PI * wheelDiameterMeters) * gearRatio; // Encoder rotations to distance moved
        public static final double drivetrainWidthMeters = Units.inchesToMeters(28); // Distance between left and right wheels in meters
    
        // Path following PID
        //public static final double kP = 4.3789; <- value from SysId
        public static final double pathP = -0.0002;
        public static final double pathI = 0;
        public static final double pathD = 0;
    
        // Drive distance PID
        public static final double driveDistP = 0.4;
        public static final double driveDistI = 0;
        public static final double driveDistD = 0;
    
        // Auto turn PID
        public static final double turnP = 0.001;
        public static final double turnI = 0;
        //public static final double turnD = 0.0001;
        public static final double turnD = 0; // 0 D for initial test
    
        // Drivetrain characterization (Do not change, found using SysId)
        public static final double kS = 0.17247;
        public static final double kV = 2.8886;
        public static final double kA = 2.1367;
    
        // Ramsete config (Do not change, default values from documentation)
        public static final double kB = 2.0;
        public static final double kZeta = 0.7;

        public static final double autoBalanceSpeed = 0.2;

        public static final double maxTrajectoryVel = 1;
        public static final double maxTrajectoryAcc = 0.5;
    }
    public final static class ShuffleboardConfig {
        public static final boolean drivetrainPrintsEnabled = true;
    }
}
