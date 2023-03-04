package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.RotatingArmConfig;

public class Controls {
    XboxController driver;
    XboxController operator;

    private double threshold = DrivetrainConfig.axisThreshold;

    // Create a new Controls
    public Controls(XboxController driver, XboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }

    LinearFilter driveSpeedFilter = LinearFilter.singlePoleIIR(0.15, 0.02);
    /** Return the left joystick's Y as long as it's over the threshold. */
    public double getDriveSpeed() {
        double joystickValue = driver.getLeftY();
        joystickValue = MathUtil.applyDeadband(-joystickValue, threshold); // deals with stick drag
        joystickValue = driveSpeedFilter.calculate(joystickValue);

        return joystickValue * DrivetrainConfig.driveSpeed;
    }

    LinearFilter turnSpeedFilter = LinearFilter.singlePoleIIR(0.1, 0.02);
    /** Return the right joystick's X as long as it's over the threshold. */
    public double getTurnSpeed() {
        double joystickValue = driver.getRightX();
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold); // deals with stick drag
        joystickValue = turnSpeedFilter.calculate(joystickValue);

        return joystickValue * DrivetrainConfig.turnSpeed;
    }

    public double getArmRotationSpeed() {
        double joystickValue = operator.getRightTriggerAxis() - operator.getLeftTriggerAxis();
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold);
        return Math.signum(joystickValue) * ((joystickValue > 0) ? RotatingArmConfig.manualUpSpeed : RotatingArmConfig.manualDownSpeed);
    }
}
