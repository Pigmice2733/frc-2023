// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.commands.drivetrain.DriveOntoChargeStation;
import frc.robot.commands.lights.strip.RunningColor;
import frc.robot.commands.routines.BalanceRoutine;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LightStrip;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  // private final LightsPanel lightPanel = new LightsPanel();
  private final LightStrip lightStrip = new LightStrip();

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
    drivetrain.setDefaultCommand(
        new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));
    lightStrip.setDefaultCommand(new RunningColor(lightStrip));
    // lightPanel.setDefaultCommand(new ScrollSponsors(lightPanel));
  }

  private void configureAutoChooser() {
    driverTab.addString("FACE TOWARDS GRID", () -> "(do it)");
    List<Command> autoCommands = List.of(
        new DriveDistancePID(drivetrain, -4)
            .withName("Only Leave Community [Driver Left or Right]"),
        new BalanceRoutine(drivetrain, true)
            .withName("Only Balance [Center]"),
        new SequentialCommandGroup(
            new DriveOntoChargeStation(drivetrain, true),
            new DriveOntoChargeStation(drivetrain,
                true))
            .withName("Only Leave Community [Center]"));

    autoChooser = new SendableChooser<Command>();
    driverTab.add("Auto Chooser", autoChooser);

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

    /**
     * [driver] Enable slow mode when Y or RightBumper pressed, disable when
     * released
     */
    new JoystickButton(driver, Button.kY.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));
    new JoystickButton(driver, Button.kRightBumper.value)
        .onTrue(new InstantCommand(drivetrain::enableSlow))
        .onFalse(new InstantCommand(drivetrain::disableSlow));

    final BalanceRoutine autoBalance = new BalanceRoutine(drivetrain);
    new JoystickButton(driver, Button.kB.value)
        .onTrue(autoBalance)
        .onFalse(new InstantCommand(() -> {
          autoBalance.cancel();
        }));

    /** [operator] Signal lights for cube */
    new JoystickButton(operator, Button.kBack.value)
        .onTrue(new InstantCommand(() -> lightStrip.signalForCube()));

    /** [operator] Signal lights for cone */
    new JoystickButton(operator, Button.kStart.value)
        .onTrue(new InstantCommand(() -> lightStrip.signalForCone()));
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
