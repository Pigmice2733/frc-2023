package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class LightStrip extends SubsystemBase {
    private Spark blinkinController;
    // make sure color 1 is purple (cube) and color 2 is yellow (cone) on blinkin
    // (physically with screwdriver)
    private double coneProgram = 0.99;
    private double cubeProgram = 0.99;

    public LightStrip() {
        this.blinkinController = new Spark(0);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void setBlinkinProgram(double program) {
        this.blinkinController.set(program);
    }

    private void signalForObject(SignaledObject object) {
        setSignaledObject(object);
        switch (object) {
            case Cone:
                setBlinkinProgram(coneProgram);
                break;
            case Cube:
                setBlinkinProgram(cubeProgram);
                break;
        }
    }

    public void signalForCube() {
        signalForObject(SignaledObject.Cube);
    }

    public void signalForCone() {
        signalForObject(SignaledObject.Cone);
    }

    public enum SignaledObject {
        Cone,
        Cube
    }

    private static SignaledObject selectedSignaledObject = SignaledObject.Cube;
    private static GenericEntry signaledObjectEntry = RobotContainer.addToDriverTab("Signaled Object",
            selectedSignaledObject.toString());

    private static void setSignaledObject(SignaledObject signaledObject) {
        selectedSignaledObject = signaledObject;
        signaledObjectEntry.setString(signaledObject.toString());

    }

    public static SignaledObject getSignaledObject() {
        return selectedSignaledObject;
    }

    public void setCubeProgram(double cubeProgram) {
        this.cubeProgram = cubeProgram;
    }

    public void setConeProgram(double coneProgram) {
        this.coneProgram = coneProgram;
    }
}
