// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.ClawConfig;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  private final DoubleSolenoid piston1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ClawConfig.piston1PortFor, ClawConfig.piston1PortRev);
  private final DoubleSolenoid piston2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ClawConfig.piston2PortFor, ClawConfig.piston2PortRev);
  
  /** Creates a new Claw. */
  public Claw() {
    piston1.set(Value.kOff);
    piston2.set(Value.kOff);

    // Shuffleboard stuff, probably
  }

  @Override
  public void periodic() {}

  public void closeClaw() {
    piston1.set(Value.kForward);
    piston2.set(Value.kForward);
  }

  public void openClaw() {
    piston1.set(Value.kReverse);
    piston2.set(Value.kReverse);
  }
}
