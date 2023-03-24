package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Colors;
import frc.robot.lights.Images;
import frc.robot.subsystems.LightsPanel;

public class CGOLLights extends CommandBase {
    private byte[][] board = {
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

    private byte[][] baseImage;

    private LightsPanel lights;
    private double lastFrameTimestamp = 0;
    private double initializeTimestamp = 0;

    public CGOLLights(LightsPanel lights) {
        this(lights,
                new byte[][][] {
                        Images.PIGMICE.getBuffer(),
                        Images.IZZY_PIGMICE.getBuffer(),
                        Images.OWEN_PIGMICE.getBuffer()
                }[(int) Math.floor(Math.random() * 3)]);
    }

    public CGOLLights(LightsPanel lights, byte[][] img) {
        this.lights = lights;

        this.baseImage = img;

        // for (int y = 0; y < board.length; y++) {
        // for (int x = 0; x < board[0].length; x++) {
        // board[y][x] = (byte) (Math.round(Math.random()) * Colors.PURPLE);
        // }
        // }

        addRequirements(this.lights);
    }

    @Override
    public void initialize() {
        initializeTimestamp = Timer.getFPGATimestamp();
        for (int y = 0; y < baseImage.length; y++) {
            for (int x = 0; x < baseImage[y].length; x++) {
                board[y][x] = baseImage[y][x];
            }
        }
    }

    private int count_neighbors(int x, int y) {
        int neighbors = 0;
        // adjacent
        if (x > 0)
            neighbors += Math.signum(board[y][x - 1]);
        if (y > 0)
            neighbors += Math.signum(board[y - 1][x]);
        if (x < board[0].length - 1)
            neighbors += Math.signum(board[y][x + 1]);
        if (y < board.length - 1)
            neighbors += Math.signum(board[y + 1][x]);

        // diagonal
        if (x > 0 && y > 0)
            neighbors += Math.signum(board[y - 1][x - 1]);
        if (x > 0 && y < board.length - 1)
            neighbors += Math.signum(board[y + 1][x - 1]);
        if (x < board[0].length - 1 && y > 0)
            neighbors += Math.signum(board[y - 1][x + 1]);
        if (x < board[0].length - 1 && y < board.length - 1)
            neighbors += Math.signum(board[y + 1][x + 1]);

        return neighbors;
    }

    private void process_board() {
        byte[][] new_board = {
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

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                int neighbors = this.count_neighbors(x, y);
                if (neighbors < 2)
                    new_board[y][x] = 0;
                else if (neighbors == 3)
                    new_board[y][x] = Colors.PURPLE;
                else if (neighbors > 3)
                    new_board[y][x] = 0;
                else
                    new_board[y][x] = board[y][x];
            }
        }

        board = new_board.clone();
    }

    @Override
    public void execute() {
        double currentTimestamp = Timer.getFPGATimestamp();
        if (currentTimestamp - initializeTimestamp < 1) {
            lights.displayGrid(board);
            return;
        }
        if (currentTimestamp - lastFrameTimestamp < 0.5)
            return;
        lastFrameTimestamp = currentTimestamp;
        process_board();
        lights.displayGrid(board);
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
