package frc.robot.commands.lights.panel;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Colors;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.LightsPanel;

public class SnakeGame extends CommandBase {
    private LightsPanel lights;
    private Queue<Vec2i> snake = new LinkedList<>();
    private int snakeLength = 1;
    private int snakeDirection = 1; // 0 = up, 1 = right, 2 = down, 3 = left
    private Vec2i snakePos;
    private Vec2i foodPos;
    private boolean gameRunning = false;
    private static final byte SNAKE_COLOR = 2;

    private BitSet board = new BitSet(16 * 16);

    public SnakeGame(LightsPanel lights) {
        this.lights = lights;
    }

    @Override
    public void initialize() {
        startGame();
    }

    @Override
    public void execute() {
        if (!gameRunning)
            return;
        // get input
        if (DriverStation.getStickPOV(1, 0) > 0) {
            snakeDirection = 0;
        } else if (DriverStation.getStickPOV(1, 90) > 0) {
            snakeDirection = 1;
        } else if (DriverStation.getStickPOV(1, 180) > 0) {
            snakeDirection = 2;
        } else if (DriverStation.getStickPOV(1, 270) > 0) {
            snakeDirection = 3;
        }

        // move snake head in direction
        switch (snakeDirection) {
            case 0:
                snakePos.y--;
                break;
            case 1:
                snakePos.x++;
                break;
            case 2:
                snakePos.y++;
                break;
            case 3:
                snakePos.x--;
                break;
        }

        // wrap snake around grid
        snakePos.x %= LightsPanel.LED_GRID_W;
        snakePos.y %= LightsPanel.LED_GRID_W;

        if (snakePos.x < 0) {
            snakePos.x += LightsPanel.LED_GRID_W;
        }

        if (snakePos.y < 0) {
            snakePos.y += LightsPanel.LED_GRID_W;
        }

        // add snake head to queue
        snake.add(snakePos);
        board.set(snakePos.toIndex(), true);

        if (snakePos.equals(foodPos)) {
            snakeLength++;
            spawnFood();
        }

        // remove snake tail from queue
        while (snake.size() > snakeLength) {
            Vec2i oldPos = snake.remove();
            board.set(oldPos.toIndex(), 0);
        }

        // test collision with snake tail or food
        if (snake.contains(snakePos)) {
            endGame();
            return;
        }

        displaySnake();
    }

    private void spawnFood() {
        int newX = (int) (Math.random() * LightsPanel.LED_GRID_W);
        int newY = (int) (Math.random() * LightsPanel.LED_GRID_W);
        this.foodPos = new Vec2i(newX, newY);
    }

    private void displaySnake() {
        this.lights.displayGrid(board, SNAKE_COLOR);
    }

    private void startGame() {
        this.gameRunning = true;
        snakePos = new Vec2i(0, 0);
        snake.add(snakePos);
        spawnFood();
    }

    private void endGame() {
        this.gameRunning = false;
        this.lights.scrollText("Game Over", null, TextScrollDirection.LEFT, 0, Colors.BLUE, 1, 10);
    }

    private static class Vec2i {
        public int x;
        public int y;

        public Vec2i(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int toIndex() {
            return this.y * LightsPanel.LED_GRID_W + this.x;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Vec2i) {
                Vec2i other = (Vec2i) obj;
                return other.x == x && other.y == y;
            }

            return false;
        }
    }
}
