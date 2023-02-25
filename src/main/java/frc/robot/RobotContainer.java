// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.automated.PickUpObjectFromHuman;
import frc.robot.commands.automated.ScoreObject;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveDistanceConstant;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.commands.drivetrain.TurnDegreesPID;
import frc.robot.commands.rotatingArm.RotateArmManual;
import frc.robot.commands.rotatingArm.RotateArmToScoreHeight;
import frc.robot.commands.rotatingArm.RotateArmToScoreHeight.ScoreHeight;
import frc.robot.commands.routines.BalanceRoutine;
import frc.robot.commands.routines.ScoreAndBalance;
import frc.robot.commands.vision.AlignAndScore;
import frc.robot.commands.vision.AlignToScore;
import frc.robot.commands.vision.FullyAlign;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.RotatingArm;
import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Vision vision = new Vision();
  private final RotatingArm arm = new RotatingArm();
  private final Claw claw = new Claw();
  private final Lights lights = new Lights();

  private final XboxController driver = new XboxController(0);
  private final XboxController operator = new XboxController(1);
  private final Controls controls = new Controls(driver, operator);

  private static final ShuffleboardTab driverTab = Shuffleboard.getTab("Drive Info");
  public static GenericEntry addToDriverTab(String name, Object defaultValue) {
    return driverTab.add(name, defaultValue).getEntry();
  }

  private SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureDefaultCommands();
    configureAutoChooser();
    configureButtonBindings();

    RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Left);
  }

  private void configureDefaultCommands() {
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));
    arm.setDefaultCommand(new RotateArmManual(arm, controls::getArmRotationSpeed));
  }

  private void configureAutoChooser() {
    List<Command> autoCommands = List.of(
      new DriveDistanceConstant(drivetrain, 2),
      new DriveDistancePID(drivetrain, 2),
      new BalanceRoutine(drivetrain),
      new TurnDegreesPID(drivetrain, 90),
      new ScoreAndBalance(drivetrain, null, null),
      new ScoreAndBalance(drivetrain, null, null, TargetLocation.Right),
      new ScoreAndBalance(drivetrain, null, null, TargetLocation.Left)
    );

    autoCommands.get(0).setName("Drive 2 Meters Constant");
    autoCommands.get(1).setName("Drive 2 Meters PID");
    autoCommands.get(2).setName("Balance");
    autoCommands.get(3).setName("Turn 180 Degrees");
    autoCommands.get(4).setName("Score and Balance");
    autoCommands.get(5).setName("Score and Balance [Right]");
    autoCommands.get(6).setName("Score and Balance [Left]");

    autoChooser = new SendableChooser<Command>();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    autoCommands.forEach(command -> {
      autoChooser.addOption(command.getName(), command);
    });

    autoChooser.setDefaultOption("None", new WaitCommand(1));
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

    new JoystickButton(driver, Button.kY.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));
    new JoystickButton(driver, Button.kRightBumper.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));

    /** [driver] Schedule AlignToScore when A is pressed, cancel when released */
    final FullyAlign alignCommand = new FullyAlign(drivetrain, vision);
    new JoystickButton(driver, Button.kA.value)
        .onTrue(alignCommand)
        .onFalse(new InstantCommand(() -> {
          alignCommand.cancel();
        }));

    /** [driver] Schedule AlignAndScore when X is pressed, cancel when released */
    final AlignAndScore alignAndScore = new AlignAndScore(vision, drivetrain, arm, claw);
    new JoystickButton(driver, Button.kX.value)
        .onTrue(alignAndScore)
        .onFalse(new InstantCommand(() -> {
          alignAndScore.cancel();
        }));

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
        .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Center)));
    new POVButton(driver, 90) // right
        .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Right)));
    new POVButton(driver, 270) // left
        .onTrue(new InstantCommand(() -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Left)));



    // OPERATOR
    /** [operator] Set the ScoreHeight in ScoreObject with D-pad */
    new POVButton(operator, 0) // up
        .onTrue(new InstantCommand(() -> RotateArmToScoreHeight.setScoreHeight(ScoreHeight.High)));
    new POVButton(operator, 90) // right
        .onTrue(new InstantCommand(() -> RotateArmToScoreHeight.setScoreHeight(ScoreHeight.Mid)));
    new POVButton(operator, 270) // left
        .onTrue(new InstantCommand(() -> RotateArmToScoreHeight.setScoreHeight(ScoreHeight.Mid)));
    new POVButton(operator, 180) // down
        .onTrue(new InstantCommand(() -> RotateArmToScoreHeight.setScoreHeight(ScoreHeight.Floor)));

    /** [operator] Open Claw */
    new JoystickButton(operator, Button.kRightBumper.value)
        .onTrue(new InstantCommand(claw::openClaw));

    /** [operator] Close Claw */
    new JoystickButton(operator, Button.kLeftBumper.value)
        .onTrue(new InstantCommand(claw::closeClaw));

    /** [operator] Schedule ScoreObject when Y is pressed, cancel when released */
    new JoystickButton(operator, Button.kY.value)
        .whileTrue(new ScoreObject(drivetrain, arm, claw));

    /** [operator] Schedule PickUpObjectFromHuman when A is pressed, cancel when released */
    new JoystickButton(operator, Button.kA.value)
        .whileTrue(new PickUpObjectFromHuman(arm, claw, drivetrain));

    /** [operator] Schedule RotateArmToScoreHeight when B is pressed, cancel when released */
    new JoystickButton(operator, Button.kB.value)
        .whileTrue(new RotateArmToScoreHeight(arm));

    /** [operator] Signal lights for cube */
    new JoystickButton(driver, Button.kBack.value)
        .onTrue(new InstantCommand(() -> lights.signalForCube()));

    /** [operator] Signal lights for cone */
    new JoystickButton(driver, Button.kStart.value)
        .onTrue(new InstantCommand(() -> lights.signalForCone()));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
