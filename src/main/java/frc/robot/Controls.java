package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.RotatingArmConfig;
import frc.robot.subsystems.RotatingArm;

public class Controls {
    XboxController driver;
    XboxController operator;

    private double threshold = DrivetrainConfig.axisThreshold;

    private RotatingArm arm;

    // Create a new Controls
    public Controls(XboxController driver, XboxController operator, RotatingArm arm) {
        this.driver = driver;
        this.operator = operator;
        this.arm = arm;
    }

    LinearFilter driveSpeedFilter = LinearFilter.singlePoleIIR(0.2, 0.02);

    /** Return the left joystick's Y as long as it's over the threshold. */
    public double getDriveSpeed() {
        double joystickValue = driver.getLeftY();
        joystickValue = MathUtil.applyDeadband(-joystickValue, threshold); // deals with stick drag
        joystickValue = driveSpeedFilter.calculate(joystickValue);

        double armAngleMultiplier = calcArmAngleMultiplier();
        SmartDashboard.putNumber("Arm Angle Multiplier", armAngleMultiplier);
        return joystickValue * DrivetrainConfig.driveSpeed * armAngleMultiplier;
    }

    LinearFilter turnSpeedFilter = LinearFilter.singlePoleIIR(0.1, 0.02);

    /** Return the right joystick's X as long as it's over the threshold. */
    public double getTurnSpeed() {
        double joystickValue = driver.getRightX();
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold); // deals with stick drag
        joystickValue = turnSpeedFilter.calculate(joystickValue);

        return joystickValue * DrivetrainConfig.turnSpeed * calcArmAngleMultiplier();
    }

    public double getArmRotationSpeed() {
        double joystickValue = operator.getRightTriggerAxis() - operator.getLeftTriggerAxis();
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold);
        return Math.signum(joystickValue) * RotatingArmConfig.manualSpeed;
    }

    private double calcArmAngleMultiplier() {
        double minSpeed = DrivetrainConfig.armUpSlower;
        double angle = arm.getAngle();
        if(angle < 30) {
            return 1;
        } else if(angle > 60) {
            return minSpeed;
        } else {
            return ((minSpeed - 1)/30) * (angle - 30) + 1;
        }

    }
}
