// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.autoDrive.DriveDistancePID;
import frc.robot.commands.drivetrain.autoDrive.TurnDegreesPID;
import frc.robot.commands.objectManipulation.ScoreObject;
import frc.robot.commands.rotatingArm.RotateArmToAngle.ArmHeight;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

/** Score, turns around (if specified), and balances. Always start robot at center facing the grid. 
 * Will turn robot around if specified before balancing */
public class ScoreAndBalance extends SequentialCommandGroup {
  public ScoreAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw, boolean turnAround) {
    addCommands(new ScoreObject(drivetrain, arm, claw, ArmHeight.High, false, false),
      arm.setSetpointCommand(ArmHeight.Floor));

    if (turnAround)
      addCommands(new DriveDistancePID(drivetrain, -1).withTimeout(2),
        new TurnDegreesPID(drivetrain, 180));

    addCommands(new BalanceRoutine(drivetrain, true));
    addRequirements(drivetrain, arm, claw);
  }

  /** Score and balances. Always start robot at center */
  public ScoreAndBalance(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    this(drivetrain, arm, claw, false);
  }
}
