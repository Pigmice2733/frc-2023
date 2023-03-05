package frc.robot.commands.drivetrain.balance;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveOverChargeStation extends CommandBase {
    private Drivetrain drivetrain;
    private boolean backwards = false;
    private boolean hasLeveledOnce = false;
    private boolean hasReachedOtherSide = false;
    private boolean isFinallyLevel = false;
    private double levelTimestamp;

    public DriveOverChargeStation(Drivetrain drivetrain, boolean backwards) {
        this.drivetrain = drivetrain;
        this.backwards = backwards;
        this.hasLeveledOnce = false;
        this.hasReachedOtherSide = false;
        this.isFinallyLevel = false;

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
        SmartDashboard.putBoolean("Is Finally Level", isFinallyLevel);
        SmartDashboard.putBoolean("Is Level", Math.abs(this.drivetrain.getPitch()) < 3);
        if (Math.abs(pitch) < 3 && !hasLeveledOnce)
            hasLeveledOnce = true;
        if ((backwards ? pitch < -8 : pitch > 8) && hasLeveledOnce) 
            hasReachedOtherSide = true;
        if (Math.abs(this.drivetrain.getPitch()) < 3 && hasReachedOtherSide && !isFinallyLevel) {
            isFinallyLevel = true;
            levelTimestamp = Timer.getFPGATimestamp();
        }

        this.drivetrain.arcadeDrive((backwards ? -1 : 1) * 0.2, 0);
    }

    @Override
    public boolean isFinished() {
        System.out.println(Timer.getFPGATimestamp() + " " + (levelTimestamp + 0.5));
        return (isFinallyLevel && Timer.getFPGATimestamp() > (levelTimestamp + 0.5));
    }

}
