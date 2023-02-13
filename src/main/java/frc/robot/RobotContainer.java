// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.RuntimeTrajectoryGenerator.TargetType;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.routines.BalanceRoutine;
import frc.robot.commands.routines.ScoreAndBalance;
import frc.robot.commands.vision.AlignAndScore;
import frc.robot.commands.vision.AlignToScore;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Vision vision = new Vision();
  // private final RotatingArm arm = new RotatingArm();
  // private final Elevator elevator = new Elevator();
  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);
  private final Controls controls = new Controls(driver, operator);
  //private final SendableChooser<RuntimeTrajectoryGenerator.TargetType> targetTypeChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));

    // targetTypeChooser.addOption("LEFT CONE", TargetType.ConeLeft);
    // targetTypeChooser.setDefaultOption("CUBE", TargetType.Cube);
    // targetTypeChooser.addOption("RIGHT CONE", TargetType.ConeRight);
    // targetTypeChooser.addOption("LEFT PICKUP", TargetType.PickupLeft);
    // targetTypeChooser.addOption("RIGHT PICKUP", TargetType.PickupRight);

    // Shuffleboard.getTab("Drivetrain").add("Target", targetTypeChooser).withWidget(BuiltInWidgets.kComboBoxChooser);

    configureButtonBindings();
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
    // DRIVER

    /** [driver] Enable slow mode when Y or Start is pressed, stop slow mode when released */
    new JoystickButton(driver, Button.kY.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));
    new JoystickButton(driver, Button.kRightBumper.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));


    /** [driver] Schedule AlignToScore when A is pressed, cancel when released */
    final AlignToScore alignToScore = new AlignToScore(vision, drivetrain);
    new JoystickButton(driver, Button.kA.value)
        .onTrue(alignToScore)
        .onFalse(new InstantCommand(() -> {
          alignToScore.cancel();
    }));

    /** [driver] Schedule AlignAndScore when X is pressed, cancel when released */
    final AlignAndScore alignAndScore = new AlignAndScore(vision, drivetrain);
    new JoystickButton(driver, Button.kX.value)
        .onTrue(alignAndScore)
        .onFalse(new InstantCommand(() -> {
          alignAndScore.cancel();
    }));

    /** [driver] Schedule AutoBalanceWithRoll when B is pressed, cancel when released */
    final BalanceRoutine autoBalance = new BalanceRoutine(drivetrain);
    new JoystickButton(driver, Button.kB.value)
        .onTrue(autoBalance)
        .onFalse(new InstantCommand(() -> {
          autoBalance.cancel(); 
    }));

    /** [driver] Force reset odometry when Start is pressed */
    new JoystickButton(driver, Button.kStart.value)
        .onTrue(new InstantCommand(() -> drivetrain.resetOdometry()));

    /** [driver] Set the TargetType in RuntimeTrajectoryGenerator with D-pad */
    new POVButton(driver, 0) // up
      .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetType.Cube)));
    new POVButton(driver, 90) // right
      .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetType.ConeRight)));
    new POVButton(driver, 270) // left
      .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetType.ConeLeft)));

    // OPERATOR


    // OLD

  
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new ScoreAndBalance(drivetrain, null, null);
  }
}
