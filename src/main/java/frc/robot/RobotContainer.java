package frc.robot;

import java.util.List;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.RumbleController;
import frc.robot.commands.drivetrain.autoDrive.DriveDistancePID;
import frc.robot.commands.drivetrain.defaultCommands.ArcadeDrive;
import frc.robot.commands.lights.panel.RotatingPanelSequence;
import frc.robot.commands.lights.strip.RunningColor;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.commands.rotatingArm.RotateArmManual;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.commands.routines.BalanceRoutine;
import frc.robot.commands.routines.LeaveAndBalance;
import frc.robot.commands.routines.RunAutoRoutineWithNavxCheck;
import frc.robot.commands.routines.ScoreAndBalance;
import frc.robot.commands.routines.ScoreAndLeave;
import frc.robot.commands.routines.ScoreAndLeaveAndBalance;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LightStrip;
import frc.robot.subsystems.LightsPanel;
import frc.robot.subsystems.RotatingArm;

/**
 * This class is where the bulk of the robot should be declared. Since
 * 
 * @@ -53,202 +35,107 @@
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
        private final Drivetrain drivetrain = new Drivetrain();
        // private final Vision vision = new Vision();
        public final RotatingArm arm = new RotatingArm();
        private final Claw claw = new Claw();
        private final LightsPanel lightPanel = new LightsPanel();
        private final LightStrip lightStrip = new LightStrip();

        private final XboxController driver = new XboxController(0);
        private final XboxController operator = new XboxController(1);
        private final Controls controls = new Controls(driver, operator, arm, drivetrain);

        public static final ShuffleboardTab driverTab = Shuffleboard.getTab("Drive Info");

        public static GenericEntry addToDriverTab(String name, Object defaultValue, int xPos, int yPos) {
                return driverTab.add(name, defaultValue).withPosition(xPos, yPos).getEntry();
        }

        private final GenericEntry driverControllerEntry;
        private final GenericEntry operatorControllerEntry;

        private void updateControllerEntries() { // Called in Robot.robotPeriodic()
                driverControllerEntry.setBoolean(driver.isConnected());
                operatorControllerEntry.setBoolean(operator.isConnected());
        }

        public void periodic() {
                this.updateControllerEntries();
                if (claw.isOpen() && !claw.didJustOpen() && claw.canGrabGamepiece()) {
                        CommandScheduler.getInstance().schedule(new RumbleController(operator, RumbleType.kLeftRumble));
                }
        }

        public void disable() {
                this.driver.setRumble(RumbleType.kBothRumble, 0);
                this.operator.setRumble(RumbleType.kBothRumble, 0);
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
                DriverStation.silenceJoystickConnectionWarning(true);

                operatorControllerEntry = addToDriverTab("Operator Controller", false, 4, 2);
                driverControllerEntry = addToDriverTab("Driver Controller", false, 3, 2);
        }

        private void configureDefaultCommands() {
                drivetrain.setDefaultCommand(
                                new ArcadeDrive(drivetrain, controls::getDriveSpeed, controls::getTurnSpeed));
                arm.setDefaultCommand(new RotateArmManual(arm, controls::getArmRotationSpeed));
                lightStrip.setDefaultCommand(new RunningColor(lightStrip));
                lightPanel.setDefaultCommand(new RotatingPanelSequence(lightPanel));
        }

        private void configureAutoChooser() {
                driverTab.addString("1", () -> "Select Auto Command").withPosition(0, 0).withSize(3, 1);
                driverTab.addString("2", () -> "Face Robot Towards Grid ~1m Away").withPosition(0, 1).withSize(3, 1);
                driverTab.addString("3", () -> "Check Controllers").withPosition(0, 2).withSize(3, 1);

                List<Command> autoCommands = List.of(
                                new DriveDistancePID(drivetrain, -4)
                                                .withName("Only Leave Community [Driver Left or Right]"),
                                new BalanceRoutine(drivetrain, true)
                                                .withName("Only Balance [Center]"),
                                new ScoreAndLeave(drivetrain, arm, claw)
                                                .withName("Score and Leave [Driver Left or Right]"),
                                new ScoreAndBalance(drivetrain, arm, claw, false)
                                                .withName("Score and Balance [Center]"),
                                new RunAutoRoutineWithNavxCheck(
                                                new LeaveAndBalance(drivetrain, TargetLocation.Left, arm),
                                                drivetrain).withName("Leave and Balance [Field Outside]"),
                                new RunAutoRoutineWithNavxCheck(
                                                new LeaveAndBalance(drivetrain, TargetLocation.Right, arm),
                                                drivetrain).withName("Leave and Balance [Field Inside]"),
                                new RunAutoRoutineWithNavxCheck(
                                                new ScoreAndLeaveAndBalance(drivetrain, arm, claw, TargetLocation.Left),
                                                drivetrain).withName("Score, Leave, and Balance [Field Outside]"),
                                new RunAutoRoutineWithNavxCheck(
                                                new ScoreAndLeaveAndBalance(drivetrain, arm, claw,
                                                                TargetLocation.Right),
                                                drivetrain).withName("Score, Leave, and Balance [Field Inside]"),
                                new ScoreObject(drivetrain, arm, claw, ArmHeight.High, false)
                                                .withName("Score Cube"));

                autoChooser = new SendableChooser<Command>();
                driverTab.add("Auto Chooser", autoChooser).withPosition(3, 0);

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

                // new JoystickButton(driver, Button.kLeftBumper.value)
                // .onTrue(new InstantCommand(drivetrain::enableBoost))
                // .onFalse(new InstantCommand(drivetrain::diableBoost));

                // /** [driver] Schedule AlignToScore when A is pressed, cancel when released */
                // new JoystickButton(driver, Button.kA.value)
                // .whileTrue(new FullyAlign(drivetrain, vision));

                /**
                 * [driver] Schedule AlignAndScore when X is pressed, cancel when released
                 */
                // new JoystickButton(driver, Button.kX.value)
                // .whileTrue(new HoldPosition(drivetrain));

                /** [driver] Schedule BalanceRoutine when B is pressed, cancel when released */
                // new JoystickButton(driver, Button.kB.value)
                // .whileTrue(new BalanceRoutine(drivetrain, true));

                /** [driver] Set the TargetType in RuntimeTrajectoryGenerator with D-pad */
                // new POVButton(driver, 0) // up
                // .onTrue(new InstantCommand(
                // () -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Center)));
                // new POVButton(driver, 90) // right
                // .onTrue(new InstantCommand(
                // () -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Right)));
                // new POVButton(driver, 270) // left
                // .onTrue(new InstantCommand(
                // () -> RuntimeTrajectoryGenerator.setTargetType(TargetLocation.Left)));

                // OPERATOR

                /** [operator] Set the ScoreHeight in ScoreObject with D-pad */
                new POVButton(operator, 0) // up
                                .onTrue(arm.setSetpointCommand(ArmHeight.High));
                new POVButton(operator, 90) // right
                                .onTrue(arm.setSetpointCommand(ArmHeight.HumanPlayer));
                new POVButton(operator, 270) // left
                                .onTrue(arm.setSetpointCommand(ArmHeight.Floor));
                new POVButton(operator, 180) // down
                                .onTrue(arm.setSetpointCommand(ArmHeight.Zero));

                /** [operator] Open Claw */
                new JoystickButton(operator, Button.kRightBumper.value)
                                .onTrue(claw.openClawCommand(true));

                /** [operator] Close Claw */
                new JoystickButton(operator, Button.kLeftBumper.value)
                                .onTrue(claw.closeClawCommand(true));

                /**
                 * [operator] Schedule ScoreObject when Y is pressed, cancel when released
                 */
                // new JoystickButton(operator, Button.kY.value)
                // .whileTrue(new ScoreObject(drivetrain, arm, claw, false));

                /**
                 * [operator] Schedule PickUpObject with HumanPlayer height when A is pressed,
                 * cancel when
                 * released
                 */
                // new JoystickButton(operator, Button.kA.value)
                // .whileTrue(new PickUpObject(drivetrain, arm, claw, ArmHeight.HumanPlayer,
                // false,
                // false));

                /**
                 * [operator] Schedule RotateArmToScoreHeight when B is pressed, cancel when
                 * released
                 */
                // new JoystickButton(operator, Button.kB.value)
                // .whileTrue(new RotateArmToAngle(arm));

                /**
                 * [operator] Schedule SpinIntakeWheels when X is pressed, cancel when
                 * released. This is for dispensing using intake wheels
                 */
                // new JoystickButton(operator, Button.kX.value)
                // .whileTrue(new SpinIntakeWheels(claw, false));

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