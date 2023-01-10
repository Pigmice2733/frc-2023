package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    XboxController driver;
    XboxController operator;

    //private double threshold = DrivetrainConfig.axisThreshold;

    // Create a new Controls
    public Controls(XboxController driver, XboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }
}