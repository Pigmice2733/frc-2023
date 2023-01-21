package frc.robot.commands.lights;

import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.Lights;

public class ScrollSponsors extends CommandBase {
    private static final List<String> SPONSORS = List.of("Daimler", "Terrazign", "Boeing", "Comcast", "CSM", "Autodesk",
            "Lam Research");

    private static int lastIndex = 0;
    private Lights lights;

    public ScrollSponsors(Lights lights) {
        this.lights = lights;

        addRequirements(lights);
    }

    private void scrollSponsors() {
        System.out.println("SHOWING " + SPONSORS.get(lastIndex));
        lights.scrollText(SPONSORS.get(lastIndex), TextScrollDirection.LEFT, 0, Images.P, 3);
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
