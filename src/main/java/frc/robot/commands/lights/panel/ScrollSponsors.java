package frc.robot.commands.lights.panel;

import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Colors;
import frc.robot.lights.Image;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.LightsPanel;

public class ScrollSponsors extends CommandBase {
    private static final List<String> SPONSORS = List.of("Daimler Truck", "Terrazign", "Boeing", "Comcast",
            "TE Connectivity",
            "DW Fritz",
            "Clackamas Steel", "Oregon Blue Print");

    private static int lastIndex = 0;
    private LightsPanel lights;
    private static Image background = new Image();

    static {
        background.imposeText("WE", 0, 0, Colors.BLUE);
        background.imposeImage(Images.SMALL_HEART, 11, 1);
    }

    public ScrollSponsors(LightsPanel lights) {
        this.lights = lights;

        addRequirements(lights);
    }

    private void scrollSponsors() {
        lights.scrollText(SPONSORS.get(lastIndex) + "!", background, TextScrollDirection.LEFT, 8, Colors.BLUE, 1, 10);
        lastIndex = (lastIndex + 1) % SPONSORS.size();
    }

    @Override
    public void execute() {
        if (lights.isAnimationFinished()) {
            scrollSponsors();
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.lights.endAnimation();
    }

    @Override
    public boolean isFinished() {
        return lastIndex == SPONSORS.size() - 1;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
