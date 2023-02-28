package frc.robot.commands.lights.strip;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightStrip;

public class SolidColor extends CommandBase {
    private final LightStrip lightStrip;

    /**
     * Creates a new ExampleCommand.
     *
     * @param lightStrip The subsystem used by this command.
     */
    public SolidColor(LightStrip lightStrip) {
        this.lightStrip = lightStrip;

        addRequirements(lightStrip);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        lightStrip.setCubeProgram(0.89);
        lightStrip.setConeProgram(0.69);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
