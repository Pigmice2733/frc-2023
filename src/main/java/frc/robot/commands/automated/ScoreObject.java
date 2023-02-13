// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.automated;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RuntimeTrajectoryGenerator;
import frc.robot.Constants.RotatingArmConfig;
import frc.robot.RuntimeTrajectoryGenerator.TargetLocation;
import frc.robot.commands.claw.OpenClaw;
import frc.robot.commands.drivetrain.DriveDistancePID;
import frc.robot.commands.rotatingArm.RotateArmToAngleConstant;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RotatingArm;

public class ScoreObject extends SequentialCommandGroup {
  /** Creates a new ScoreObject. */
  public ScoreObject(Drivetrain drivetrain, RotatingArm arm, Claw claw) {
    double height = 0;

    TargetLocation targetType = RuntimeTrajectoryGenerator.getTargetType();

    if (selectedScoreHeight == ScoreHeight.Mid && targetType == TargetLocation.Center) { height = 0; }
    else if (selectedScoreHeight == ScoreHeight.Mid && targetType != TargetLocation.Center) { height = 0; }
    else if (selectedScoreHeight == ScoreHeight.High && targetType == TargetLocation.Center) { height = 0; }
    else if (selectedScoreHeight == ScoreHeight.High && targetType != TargetLocation.Center) { height = 0; }
    else {height = 0; } // Floor

    height -= RotatingArmConfig.armLength-RotatingArmConfig.armHeight;
    double armAngle = -Math.acos(MathUtil.clamp(height/(RotatingArmConfig.armLength*2) - 1, -1.0, 1.0)) + Math.PI;

    addCommands(
      new DriveDistancePID(drivetrain, -0.5),
      new RotateArmToAngleConstant(arm, armAngle),
      new DriveDistancePID(drivetrain, 0.5),
      new OpenClaw(claw),
      new DriveDistancePID(drivetrain, 0.5),
      new RotateArmToAngleConstant(arm, 0)
    );

    addRequirements(drivetrain, arm, claw);
  }
          
  public enum ScoreHeight {
    Floor,
    Mid,
    High
  }

  private static ScoreHeight selectedScoreHeight = ScoreHeight.Floor;

  public static void setScoreHeight(ScoreHeight targetType) { selectedScoreHeight = targetType; }
  public static ScoreHeight getScoreHeight() { return selectedScoreHeight; }
}
