package frc.robot.commands;

import frc.robot.subsystems.RotatingArm;
import frc.robot.subsystems.Elevator;
import frc.robot.Constants.ClawConfig;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawToPoint extends CommandBase {
    double targetHeight = 0, targetDistance = 0;
    RotatingArm claw;
    Elevator elevator;

    public ClawToPoint(RotatingArm claw, Elevator elevator) {
        // input a target height and target distance
        this.claw = claw;
        this.elevator = elevator;
    }

    double armRotation = Math.acos(targetDistance / ClawConfig.armLength);
    double elevatorHeight = targetHeight - (ClawConfig.armLength * Math.sin(armRotation));

    RotateClawTo filler1 = new RotateClawTo(armRotation, claw);
    RaiseElevatorTo filler2 = new RaiseElevatorTo(elevatorHeight, elevator);
}
