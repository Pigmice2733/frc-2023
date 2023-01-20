package frc.robot.lights;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Lights;

public class Animation {
    private List<Image> images = new ArrayList<Image>();
    private double fps = 30;
    private double lastFrameTimestamp;
    private int currentFrame = 0;

    public Animation() {
        this.lastFrameTimestamp = Timer.getFPGATimestamp();
    }

    public Animation(List<Image> images) {
        this();
        this.images = images;
    }

    public Animation(List<Image> images, double fps) {
        this();
        this.images = images;
        this.fps = fps;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void addImage(int[][] buffer) {
        images.add(new Image(buffer));
    }

    public List<Image> getImages() {
        return images;
    }

    public Image getImage(int index) {
        return images.get(index);
    }

    public Image getCurrentImage() {
        return images.get(currentFrame);
    }

    private void update() {
        double currentTimestamp = Timer.getFPGATimestamp();
        if (currentTimestamp - lastFrameTimestamp >= 1.0 / fps) {
            lastFrameTimestamp = currentTimestamp;
            currentFrame++;
            currentFrame %= images.size();
        }
    }

    public void display(Lights lights) {
        lights.displayImage(getCurrentImage());
        update();
    }
}
