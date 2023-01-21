// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignWithTag;
import frc.robot.commands.AutoBalanceWithRoll;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveOntoChargeStation;
import frc.robot.commands.lights.ScrollSponsors;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Vision;

/*
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public final PIDController alignRotate = new PIDController(0, 0, 0);
  public final PIDController alignLinear = new PIDController(0, 0, 0);

  private final Drivetrain drivetrain = new Drivetrain();
  private final Lights lights = new Lights();
  private final Vision vision = new Vision(drivetrain);

  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);
  private final Controls controls = new Controls(driver, operator);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));
    // lights.setDefaultCommand(new RainbowLights(lights));
    SmartDashboard.putData("Rotate Controller", alignRotate);
    SmartDashboard.putData("Linear Controller", alignLinear);

    configureButtonBindings();

    lights.displayImage(Images.OWEN_PIGMICE);

    // CommandScheduler.getInstance().schedule(new ScrollSponsors(lights));
    // lights.scrollText("Pigmice", TextScrollDirection.LEFT, 0, 0xff0000, 3);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driver, Button.kA.value)
        .onTrue(new InstantCommand(() -> CommandScheduler.getInstance()
            .schedule(new AlignWithTag(drivetrain, vision, alignRotate, alignLinear))));

    new JoystickButton(driver, Button.kX.value)
        .onTrue(new InstantCommand(() -> drivetrain.resetOdometry()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous

    // TrajectoryConfig config = new
    // TrajectoryConfig(DrivetrainConfig.maxTrajectoryVel,
    // DrivetrainConfig.maxTrajectoryAcc).setKinematics(drivetrain.getKinematics());

    // //Trajectory trajectory = TrajectoryGenerator.generateTrajectory(List.of(new
    // Pose2d(), new Pose2d(3, -0.5, new Rotation2d()), new Pose2d(6, 0, new
    // Rotation2d()), new Pose2d(6, 0, new Rotation2d(-180)), new Pose2d(3, -0.5,
    // new Rotation2d(-180)), new Pose2d(0, 0, new Rotation2d(-180))), config);
    // Trajectory trajectory = TrajectoryGenerator.generateTrajectory(List.of(new
    // Pose2d(), new Pose2d(2, 0, new Rotation2d(-180))), config);
    // System.out.println(trajectory);

    // return new RamseteCommand(
    // trajectory,
    // drivetrain::getPose,
    // new RamseteController(DrivetrainConfig.kB, DrivetrainConfig.kZeta),
    // drivetrain.getFeedForward(),
    // drivetrain.getKinematics(),
    // drivetrain::getWheelSpeeds,
    // new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI,
    // DrivetrainConfig.pathD), // Left
    // new PIDController(DrivetrainConfig.pathP, DrivetrainConfig.pathI,
    // DrivetrainConfig.pathD), // Right
    // drivetrain::tankDriveVolts,
    // drivetrain
    // );
    // return new FollowPath(drivetrain, trajectory);

    // return new BangBangBalance(drivetrain);
    return new SequentialCommandGroup(new DriveOntoChargeStation(drivetrain), new AutoBalanceWithRoll(drivetrain));
    // return new SequentialCommandGroup(new AutoBalanceWithRoll(drivetrain));

  }
}
