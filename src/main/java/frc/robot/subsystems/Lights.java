// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.lights.Animation;
import frc.robot.lights.Image;
import frc.robot.lights.Text;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.lights.Text.TextSequence;

public class Lights extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer led_buffer;
    private final int LED_PORT = 9;
    public static final int LED_GRID_W = 16;
    public static final int LED_GRID_LEN = LED_GRID_W * LED_GRID_W;

    private final Queue<Animation> animationQueue = new LinkedList<>();
    private boolean animationIsFinished = true;

    public Lights() {
        led = new AddressableLED(LED_PORT);
        led_buffer = new AddressableLEDBuffer(LED_GRID_LEN);
        led.setLength(led_buffer.getLength());
        led.start();
    }

    public void periodic() {
        if (this.animationQueue.size() > 0) {
            Animation currentAnimation = this.animationQueue.peek();
            currentAnimation.display(this);
            if (currentAnimation.isFinished()) {
                this.endAnimation();
            }
        }
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

    public void displayImage(Image image, int x, int y) {
        Image newImage = new Image();
        newImage.imposeImage(image, x, y);
        this.displayImage(newImage);
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

    public void queueAnimation(Animation animation) {
        this.queueAnimation(animation, false);
    }

    public void queueAnimation(Animation animation, boolean interrupt) {
        animationIsFinished = false;
        if (interrupt)
            this.endAnimation();
        this.animationQueue.add(animation);
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
                    }
                }
            }
            xOffset += image[0].length + 1;
        }

        led.setData(led_buffer);
    }

    public void scrollText(String text, TextScrollDirection direction, int y, int color, int speed) {
        this.scrollText(text, direction, y, color, speed, 5);
    }

    public void scrollText(String text, TextScrollDirection direction, int y, int color, int speed, int fps) {
        this.scrollText(text, new Image(), direction, y, color, speed, speed);
    }

    public void scrollText(String text, Image background, TextScrollDirection direction, int y, int color, int speed) {
        this.scrollText(text, background, direction, y, color, speed, 5);
    }

    public void scrollText(String text, Image background, TextScrollDirection direction, int y, int color, int speed,
            int fps) {
        TextSequence letters = Text.buildLetters(text).setColor(color);
        List<Image> frames = new ArrayList<Image>();
        switch (direction) {
            case LEFT:
                for (int x = LED_GRID_W; x + letters.getLength() + speed * 2 >= 0; x -= speed) {
                    Image frame = Image.from(background);
                    int xOffset = 0;
                    for (int[][] image : letters.getLetters()) {
                        frame.imposeGrid(image, x + xOffset, y);
                        xOffset += image[0].length + 1;
                    }
                    frames.add(frame);
                }
                break;
            case RIGHT:
                for (int x = -letters.getLength(); x < LED_GRID_W; x += speed) {
                    Image frame = Image.from(background);
                    int xOffset = 0;
                    for (int[][] image : letters.getLetters()) {
                        frame.imposeGrid(image, x + xOffset, y);
                        xOffset += image[0].length + 1;
                    }
                    frames.add(frame);
                }
                break;
        }

        this.queueAnimation(new Animation(frames, fps));
    }

    public void endAnimation() {
        this.animationQueue.poll();
        this.animationIsFinished = true;
    }

    public boolean isAnimationFinished() {
        return this.animationIsFinished;
    }

    public void signalForCube() {
        setSignaledObject(SignaledObject.Cone);
    }

    public void signalForCone() {
        setSignaledObject(SignaledObject.Cube);
    }

    private enum SignaledObject {
        Cone,
        Cube
    }

    private static SignaledObject selectedSignaledObject = SignaledObject.Cube;
    private static GenericEntry signaledObjectEntry = RobotContainer.addToDriverTab("Signaled Object", selectedSignaledObject.toString());

    private static void setSignaledObject(SignaledObject signaledObject) 
    { 
        selectedSignaledObject = signaledObject; 
        signaledObjectEntry.setString(signaledObject.toString()); 
    }

    public static SignaledObject getSignaledObject() { return selectedSignaledObject; }
}
