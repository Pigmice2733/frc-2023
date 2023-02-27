package frc.robot.commands.lights.strip;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LightStrip;

public class RunningColor extends CommandBase {
    private final LightStrip lightStrip;
    private int position = 0;
    private final int maxPosition;
    private final int fadeDistance = 5;
    private final int updatesPerSecond = 5;
    private final double ticksPerUpdate = 50d / updatesPerSecond;
    private int tick = 0;
    private final int maxTick = 20;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public RunningColor(LightStrip lightStrip) {
        this.lightStrip = lightStrip;
        maxPosition = this.lightStrip.getLedLength();

        addRequirements(lightStrip);
    }

    private int getDistanceFromPos(int i) {
        // return Math.abs(i - position) % maxPosition;
        return Math.min(Math.abs(i - position) % maxPosition, Math.abs(i - position +
                maxPosition) % maxPosition);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        for (int i = 0; i < maxPosition; i++) {
            // if (i == position)
            // lightStrip.setLED(i, LightStrip.LED_COLOR);
            int distance = getDistanceFromPos(i);
            double intensity = 255d * Math.max(0, (double) (fadeDistance - distance) / fadeDistance);
            lightStrip.setPixelIntensity(i, intensity);
        }
        if (tick >= ticksPerUpdate) {
            position++;
            position %= maxPosition;
            tick = 0;
        }
        tick++;
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
