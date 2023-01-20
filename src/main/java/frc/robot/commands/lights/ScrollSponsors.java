package frc.robot.commands.lights;

import java.util.List;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.lights.Images;
import frc.robot.lights.Text.TextScrollDirection;
import frc.robot.subsystems.Lights;

public class ScrollSponsors extends InstantCommand {
    private static final List<String> SPONSORS = List.of("Daimler", "Terrazign", "Boeing", "Comcast", "CSM", "Autodesk",
            "Lam Research");

    private static int lastIndex = 0;

    public ScrollSponsors(Lights lights) {
        super(() -> {
            lights.scrollText(SPONSORS.get(lastIndex), TextScrollDirection.LEFT, 0, Images.P, 3);
            lastIndex = (lastIndex + 1) % SPONSORS.size();
        });

        addRequirements(lights);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
