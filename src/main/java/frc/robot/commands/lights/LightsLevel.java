package frc.robot.commands.lights;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lights;

public class LightsLevel extends CommandBase {
    private Drivetrain drivetrain;
    private Lights lights;

    private double lastFrameTimestamp;
    private double fps;

    public LightsLevel(Drivetrain drivetrain, Lights lights) {
        this(drivetrain, lights, 4);
    }

    public LightsLevel(Drivetrain drivetrain, Lights lights, double fps) {
        this.drivetrain = drivetrain;
        this.lights = lights;
        this.fps = fps;

        addRequirements(lights);
    }

    @Override
    public void execute() {
        double currentTimestamp = Timer.getFPGATimestamp();
        if (currentTimestamp - lastFrameTimestamp <= 1 / fps)
            return;

        lastFrameTimestamp = currentTimestamp;

        double pitch = this.drivetrain.getPitch();
        double h = Math.tan(Math.toRadians(pitch)) * 8;

        int[][] grid = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        double dx = 15;
        double dy = 2 * h;

        for (int x = 0; x < 16; x++) {
            int y = (int) Math.floor(8 + h - dy * x / dx);
            grid[y][x] = 1;
        }

        lights.displayGrid(grid);
    }
}
