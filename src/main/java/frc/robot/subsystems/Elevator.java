package frc.robot.subsystems;

import frc.robot.Constants.ElevatorConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(ElevatorConfig.motorPort, MotorType.kBrushless);

  /**
   * Create a new Elevator.
   */
  public Elevator() {
    motor.restoreFactoryDefaults();
    motor.getEncoder().setPositionConversionFactor(ElevatorConfig.rotationToDistanceConversion);
  }

  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void rise(double speed){
    motor.set(speed);
  }

  /**
   * Get the current height of the elevator.
   */
  public double getHeight(){
    return motor.getEncoder().getPosition();
  }

  /**
   * Reset the encoder to be at zero height.
   */
  public void resetEncoder(){
    motor.getEncoder().setPosition(0);
  }
}
