package frc.robot.commands.lights.panel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Image;
import frc.robot.subsystems.LightsPanel;

public class ShowImage extends CommandBase {
    private LightsPanel lights;
    private Image image;
    private boolean displayedImage = false;

    public ShowImage(LightsPanel lights, Image image) {
        this.lights = lights;
        this.image = image;

        addRequirements(this.lights);
    }

    @Override
    public void initialize() {
        this.lights.displayImage(this.image);
    }

    @Override
    public void execute() {
        if (!displayedImage) {
            this.lights.displayImage(this.image);
            displayedImage = true;
        }
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
