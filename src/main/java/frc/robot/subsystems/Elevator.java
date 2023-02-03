package frc.robot.subsystems;

import frc.robot.Constants.ElevatorConfig;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private final CANSparkMax leftMotor = new CANSparkMax(ElevatorConfig.leftMotorPort, MotorType.kBrushless);
  //private final CANSparkMax rightMotor = new CANSparkMax(ElevatorConfig.rightMotorPort, MotorType.kBrushless);

  /**
   * Create a new Elevator.
   */
  public Elevator() {
    leftMotor.restoreFactoryDefaults();
    //rightMotor.restoreFactoryDefaults();
    //rightMotor.follow(leftMotor);
    leftMotor.getEncoder().setPositionConversionFactor(ElevatorConfig.rotationToDistanceConversion);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Height", getHeight());
  }
  /** Rotate the arm.
   * @param speed the speed to rotate at
   */
  public void outputToMotors(double speed){
    leftMotor.set(speed * ElevatorConfig.speedMultipler);
  }

  /**
   * Get the current height of the elevator.
   */
  public double getHeight(){
    return leftMotor.getEncoder().getPosition();
  }

  /**
   * Reset the encoder to be at zero height.
   */
  public void resetEncoder(){
    leftMotor.getEncoder().setPosition(0);
  }
}
