package frc.robot.commands.automated;

import frc.robot.subsystems.RotatingArm;
import frc.robot.subsystems.Elevator;
import frc.robot.Constants;
import frc.robot.commands.elevator.RaiseElevatorToHeightPID;
import frc.robot.commands.rotatingArm.RotateArmToAnglePID;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClawToPoint extends CommandBase {
	double targetHeight, targetDistance;
	RotatingArm arm;
	Elevator elevator;

	/**
	 * Move the claw to a particular point in space. All units are in inches.
	 * 
	 * @param arm      the RotatingArm subsystem
	 * @param elevator the Elevator subsystem
	 * @param height   the target height above the ground
	 * @param distance the target distance away from the elevator, must be less than
	 *                 the length of the arm
	 */
	public MoveClawToPoint(RotatingArm arm, Elevator elevator, double height, double distance) {
		this.arm = arm;
		this.elevator = elevator;

		targetHeight = height - Constants.robotHeight;
		targetDistance = distance;
	}

	double armRotation = Math.acos(targetDistance / Constants.RotatingArmConfig.armLength);

	double armAngle, elevatorHeight; {
	if(targetHeight<Constants.ElevatorConfig.maxHeight) {
		armAngle = (armRotation * -180 / Math.PI) + 90;
		elevatorHeight = targetHeight + (Constants.RotatingArmConfig.armLength * Math.sin(armRotation));
	} else {
		armAngle = (armRotation * 180 / Math.PI) + 90;
		elevatorHeight = targetHeight - (Constants.RotatingArmConfig.armLength * Math.sin(armRotation));
	}}

	RotateArmToAnglePID filler1 = new RotateArmToAnglePID(armAngle, arm);
	RaiseElevatorToHeightPID filler2 = new RaiseElevatorToHeightPID(elevatorHeight, elevator);
}
