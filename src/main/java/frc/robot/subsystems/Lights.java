// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.lights.AnimateLights;
import frc.robot.lights.Animation;
import frc.robot.lights.Image;
import frc.robot.lights.Text;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.lights.Text.TextSequence;

public class Lights extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer led_buffer;
    private final int LED_PORT = 9;
    private final int LED_GRID_W = 16;
    private final int LED_GRID_LEN = LED_GRID_W * LED_GRID_W;

    public Lights() {
        led = new AddressableLED(LED_PORT);
        led_buffer = new AddressableLEDBuffer(LED_GRID_LEN);
        led.setLength(led_buffer.getLength());
        led.start();
    }

    public void periodic() {
    }

    private int mapCoordinatesToIndex(int x, int y) {
        int coord = y * LED_GRID_W;

        if (y % 2 == 1) {
            coord += x;
        } else {
            coord += LED_GRID_W;
            coord -= x + 1;
        }

        return coord;
    }

    private int[] hexToRGB(int color) {
        int r = (color & 0xff0000) >> 16;
        int g = (color & 0x00ff00) >> 8;
        int b = (color & 0x0000ff);

        return new int[] { r, g, b };
    }

    public void displayImage(Image image) {
        this.displayGrid(image.getBuffer());
    }

    public void displayGrid(int[][] image) {
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[0].length; x++) {
                int coord = mapCoordinatesToIndex(x, y);
                if (image[y][x] > 0) {
                    int[] color = hexToRGB(image[y][x]);
                    led_buffer.setRGB(coord, color[0], color[1], color[2]);
                } else
                    led_buffer.setRGB(coord, 0, 0, 0);
            }
        }
        led.setData(led_buffer);
    }

    public void displayRainbow(int HUE_OFFSET, int HUE_STEP) {
        for (int i = 0; i < LED_GRID_LEN; i++) {
            // led_buffer.setRGB(i, 75, 48, 71);
            int row = i / LED_GRID_W;
            int col = i % LED_GRID_W;
            int coord = mapCoordinatesToIndex(col, row);
            int dist = row + col;
            led_buffer.setHSV(coord, (HUE_STEP * dist + HUE_OFFSET) % 180, 255, 64);
        }

        led.setData(led_buffer);
    }

    public void displayText(String text, int x, int y, int color) {
        TextSequence letters = Text.buildLetters(text);
        int xOffset = 0;
        for (int[][] image : letters.getLetters()) {
            for (int y_2 = 0; y_2 < image.length; y_2++) {
                for (int x_2 = 0; x_2 < image[y_2].length; x_2++) {
                    int coord = mapCoordinatesToIndex(x + x_2 + xOffset, y + y_2);
                    if (coord < 0 || coord >= LED_GRID_LEN)
                        continue;
                    if (image[y_2][x_2] > 0) {
                        int[] rgb = hexToRGB(color);
                        led_buffer.setRGB(coord, rgb[0], rgb[1], rgb[2]);
                    } else
                        led_buffer.setRGB(coord, 0, 0, 0);
                }
            }
            xOffset += image[0].length + 1;
        }

        led.setData(led_buffer);
    }

    public void scrollText(String text, TextScrollDirection direction, int y, int color) {
        this.scrollText(text, direction, y, color, 5);
    }

    public void scrollText(String text, TextScrollDirection direction, int y, int color, int fps) {
        TextSequence letters = Text.buildLetters(text).setColor(color);
        List<Image> frames = new ArrayList<Image>();
        switch (direction) {
            case LEFT:
                for (int x = LED_GRID_W; x > -letters.getLength(); x--) {
                    Image frame = new Image();
                    int xOffset = 0;
                    for (int[][] image : letters.getLetters()) {
                        frame.imposeGrid(image, x + xOffset, y);
                        xOffset += image[0].length + 1;
                    }
                    frames.add(frame);
                }
                break;
            case RIGHT:
                for (int x = -letters.getLength(); x > LED_GRID_W; x--) {
                    Image frame = new Image();
                    int xOffset = 0;
                    for (int[][] image : letters.getLetters()) {
                        frame.imposeGrid(image, x + xOffset, y);
                        xOffset += image[0].length + 1;
                    }
                    frames.add(frame);
                }
                break;
        }
        Animation animation = new Animation(frames, fps);

        CommandScheduler.getInstance().schedule(new AnimateLights(this, animation));
    }
}
