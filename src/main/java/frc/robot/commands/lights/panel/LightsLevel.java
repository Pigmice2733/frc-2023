package frc.robot.commands.lights.panel;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Colors;
import frc.robot.lights.Images;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LightsPanel;

public class LightsLevel extends CommandBase {
    private Drivetrain drivetrain;
    private LightsPanel lights;

    private double lastFrameTimestamp;
    private double fps;

    public LightsLevel(Drivetrain drivetrain, LightsPanel lights) {
        this(drivetrain, lights, 4);
    }

    public LightsLevel(Drivetrain drivetrain, LightsPanel lights, double fps) {
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

        // TODO pitch might need to be inverted - needs to be tested
        double pitch = MathUtil.applyDeadband(this.drivetrain.getPitch(), 2);
        if (pitch >= 45)
            return;
        double h = Math.tan(Math.toRadians(pitch)) * 8;

        byte[][] grid = new byte[][] {
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
            for (int y2 = 0; y2 < 16; y2++) {
                if (y2 > y)
                    grid[y2][x] = 0;
                else if (y2 == y)
                    grid[y][x] = Colors.PURPLE;
                else
                    grid[y2][x] = Colors.BLUE;
            }
        }

        lights.displayGrid(grid);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
