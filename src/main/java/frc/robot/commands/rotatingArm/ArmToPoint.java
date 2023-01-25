package frc.robot.commands.rotatingArm;

import frc.robot.subsystems.RotatingArm;
import frc.robot.subsystems.Elevator;
import frc.robot.Constants.*;
import frc.robot.commands.elevator.RaiseElevatorToHeight;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawToPoint extends CommandBase {
    double targetHeight = 0, targetDistance = 0;
    RotatingArm claw;
    Elevator elevator;

    /**
     * Move the claw to a particular point in space. All units are in inches.
     * @param arm the RotatingArm subsystem
     * @param elevator the Elevator subsystem
     * @param height the target height above the ground
     */
    public ClawToPoint(RotatingArm claw, Elevator elevator, double height, double distance) {
        this.claw = claw;
        this.elevator = elevator;

        targetHeight = height - Constants.ElevatorConfig.robotHeight;
        targetDistance = distance;
    }

    double armRotation = Math.acos(targetDistance / Constants.ClawConfig.armLength);
    double elevatorHeight = targetHeight - (Constants.ClawConfig.armLength * Math.sin(armRotation));

    RotateArmToAngle filler1 = new RotateArmToAngle(armRotation, claw);
    RaiseElevatorToHeight filler2 = new RaiseElevatorToHeight(elevatorHeight, elevator);
}
