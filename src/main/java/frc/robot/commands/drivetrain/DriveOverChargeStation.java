package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveOverChargeStation extends CommandBase {
    private Drivetrain drivetrain;
    private boolean backwards = false;
    private boolean hasLeveledOnce = false;
    private boolean hasReachedOtherSide = false;

    public DriveOverChargeStation(Drivetrain drivetrain, boolean backwards) {
        this.drivetrain = drivetrain;
        this.backwards = backwards;

        addRequirements(drivetrain);
    }

    public DriveOverChargeStation(Drivetrain drivetrain) {
        this(drivetrain, false);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double pitch = this.drivetrain.getPitch();
        SmartDashboard.putBoolean("Leveled Once", hasLeveledOnce);
        SmartDashboard.putBoolean("Reached Other Side", hasReachedOtherSide);
        SmartDashboard.putBoolean("Is Level", Math.abs(this.drivetrain.getPitch()) < 3);
        if (Math.abs(pitch) < 3 && !hasLeveledOnce)
            hasLeveledOnce = true;
        if ((backwards ? pitch < -8 : pitch > 8) && hasLeveledOnce)
            hasReachedOtherSide = true;

        this.drivetrain.arcadeDrive((backwards ? -1 : 1) * 0.2, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(this.drivetrain.getPitch()) < 3 && hasReachedOtherSide;
    }

}
