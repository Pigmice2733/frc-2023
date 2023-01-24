package frc.robot.commands.lights;

import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Image;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.Lights;

public class ScrollSponsors extends CommandBase {
    private static final List<String> SPONSORS = List.of("Daimler", "Terrazign", "Boeing", "Comcast", "Clackamas Steel",
            "Autodesk",
            "Lam Research");

    private static int lastIndex = 0;
    private Lights lights;
    private static Image background = new Image();

    static {
        background.imposeText("WE", 0, 0, Images.DB);
        background.imposeImage(Images.SMALL_HEART, 11, 1);
    }

    public ScrollSponsors(Lights lights) {
        this.lights = lights;

        addRequirements(lights);
    }

    private void scrollSponsors() {
        lights.scrollText(SPONSORS.get(lastIndex) + "!", background, TextScrollDirection.LEFT, 8, Images.DB, 1, 10);
        lastIndex = (lastIndex + 1) % SPONSORS.size();
    }

    @Override
    public void execute() {
        if (lights.isAnimationFinished()) {
            scrollSponsors();
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
