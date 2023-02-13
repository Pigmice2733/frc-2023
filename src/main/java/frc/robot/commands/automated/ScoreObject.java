// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.RotatingArmConfig;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.rotatingArm.RotateArmToAngleSimple;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreObject extends SequentialCommandGroup {
  /** Creates a new ScoreObject. */
  public ScoreObject(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    double height = 0;

    switch(selectedScoreHeight) { // TODO: enter correct heights
      case CubeMid:
        height = 0;
        break;
      case CubeHigh:
        height = 0;
        break;
      case ConeMid:
        height = 0;
        break;
      case ConeHigh:
        height = 0;
        break;
      default: // Floor
        height = 0;
        break;
    }
    height -= RotatingArmConfig.armLength-RotatingArmConfig.armHeight;
    double armAngle = -Math.acos(height/(RotatingArmConfig.armLength*2) - 1) + Math.PI;

    addCommands(
      new DriveDistance(drivetrain, -0.5),
      new RotateArmToAngleSimple(arm, armAngle),
      new DriveDistance(drivetrain, 0.5),
      new OpenClaw(claw),
      new DriveDistance(drivetrain, 0.5),
      new RotateArmToAngleSimple(arm, 0)
    );

    addRequirements(drivetrain, arm, claw);
  }
          
  public enum ScoreHeight {
    Floor,
    CubeMid,
    CubeHigh,
    ConeMid,
    ConeHigh
  }

  private static ScoreHeight selectedScoreHeight = ScoreHeight.Floor;

  public static void setScoreHeight(ScoreHeight targetType) { selectedScoreHeight = targetType; }
  public static ScoreHeight getScoreHeight() { return selectedScoreHeight; }
}
