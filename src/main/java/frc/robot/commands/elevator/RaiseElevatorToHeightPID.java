package frc.robot.commands.elevator;

import frc.robot.subsystems.Elevator;
import frc.robot.Constants.ElevatorConfig;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

public class RaiseElevatorToHeightPID extends ProfiledPIDCommand {
    
    /**
     * Raise the elevator to a particular height from its base.
     * @param targetHeight height to move to above bottom of elevator in inches
     * @param elevator the elevator subsystem
     */
    public RaiseElevatorToHeightPID (double targetHeight, Elevator elevator) {
        super(
            new ProfiledPIDController(
                ElevatorConfig.kP,
                ElevatorConfig.kI,
                ElevatorConfig.kD,
                new Constraints(ElevatorConfig.maxAcceleration, ElevatorConfig.maxVelocity)),
            elevator::getHeight,
            targetHeight,
            (output, setpoint) -> {elevator.outputToMotors(output);},
            elevator
        );

        getController().setTolerance(0.1, 0.1);
        addRequirements(elevator);
    }

    @Override
    public void initialize() {}
  
    @Override
    public boolean isFinished() {
      return getController().atSetpoint();
    }
}
