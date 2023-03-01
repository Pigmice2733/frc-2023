package frc.robot.commands.lights.strip;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightStrip;

public class RunningColor extends CommandBase {
    private final LightStrip lightStrip;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public RunningColor(LightStrip lightStrip) {
        this.lightStrip = lightStrip;

        addRequirements(lightStrip);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        this.lightStrip.setCubeProgram(0.01);
        this.lightStrip.setConeProgram(0.21);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
