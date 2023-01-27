// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.RuntimeTrajectoryGenerator.TargetType;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.routines.BalanceRoutine;
import frc.robot.commands.vision.AlignToScore;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Vision vision = new Vision();

  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);
  private final Controls controls = new Controls(driver, operator);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {   
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /** Schedule AlignToScore when A is pressed, cancel when released */
    final AlignToScore alignToScore = new AlignToScore(vision, drivetrain, TargetType.Cube);
    new JoystickButton(driver, Button.kA.value) 
      .onTrue(alignToScore)
      .onFalse(new InstantCommand(() -> {
        alignToScore.cancel(); 
      }));

    /** Schedule AutoBalanceWithRoll when B is pressed, cancel when released */
    final BalanceRoutine autoBalance = new BalanceRoutine(drivetrain);
    new JoystickButton(driver, Button.kB.value) 
      .onTrue(autoBalance)
      .onFalse(new InstantCommand(() -> {
        autoBalance.cancel(); 
      }));

    /** Fast way to reset odometry for testing */
    new JoystickButton(driver , Button.kX.value)
      .onTrue(new InstantCommand(() -> drivetrain.resetOdometry()));

    /** Enable slow mode when Y is pressed, stop slow mode when released */
    new JoystickButton(driver, Button.kY.value)
      .onTrue(new InstantCommand(drivetrain::enableSlow))
      .onFalse(new InstantCommand(drivetrain::disableSlow));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
