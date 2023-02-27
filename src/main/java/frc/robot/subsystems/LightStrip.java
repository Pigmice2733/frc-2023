package frc.robot.subsystems;

import static frc.robot.subsystems.LightsPanel.hexToRGB;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.lights.RGB;

public class LightStrip extends SubsystemBase {
    private AddressableLED led;
    private AddressableLEDBuffer led_buffer;
    private final int LED_PORT = 1;
    private final int LED_LEN = 20; // TODO change this later when on robot

    public static int LED_COLOR = 0x000000;
    public static RGB LED_COLOR_RGB = new RGB(0, 0, 0);

    public LightStrip() {
        led = new AddressableLED(LED_PORT);
        led_buffer = new AddressableLEDBuffer(LED_LEN);
        led.setLength(led_buffer.getLength());
        led.start();
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public int getLedLength() {
        return this.led_buffer.getLength();
    }

    public void setLED(int index, int r, int g, int b) {
        led_buffer.setRGB(index, r, g, b);
        led.setData(led_buffer);
    }

    public void setLED(int index, int[] rgb) {
        led_buffer.setRGB(index, rgb[0], rgb[1], rgb[2]);
        led.setData(led_buffer);
    }

    public void setLED(int index, int color) {
        RGB rgb = hexToRGB(color);
        led_buffer.setRGB(index, rgb.getR(), rgb.getG(), rgb.getB());
        led.setData(led_buffer);
    }

    public void setPixelIntensity(int index, double intensity) {
        // might have to convert color to hue and adjust value based on intensity

        led_buffer.setRGB(index,
                (int) (LED_COLOR_RGB.getR() * intensity / 255d),
                (int) (LED_COLOR_RGB.getG() * intensity / 255d),
                (int) (LED_COLOR_RGB.getB() * intensity / 255d));
        led.setData(led_buffer);
    }

    public void setSolidColor() {
        for (int i = 0; i < LED_LEN; i++) {
            led_buffer.setRGB(i, LED_COLOR_RGB.getR(), LED_COLOR_RGB.getG(), LED_COLOR_RGB.getB());
        }
        led.setData(led_buffer);
    }

    private void displayGameObject(SignaledObject object) {
        switch (object) {
            case Cube:
                LED_COLOR = 0x6a00aa;
                break;
            case Cone:
                LED_COLOR = 0xFFFF00;
                break;
        }
        LED_COLOR_RGB = hexToRGB(LED_COLOR);
    }

    private void signalForObject(SignaledObject object) {
        setSignaledObject(object);
        displayGameObject(object);
    }

    public void signalForCube() {
        signalForObject(SignaledObject.Cube);
    }

    public void signalForCone() {
        signalForObject(SignaledObject.Cone);
    }

    private enum SignaledObject {
        Cone,
        Cube
    }

    private static SignaledObject selectedSignaledObject = SignaledObject.Cube;
    private static GenericEntry signaledObjectEntry = RobotContainer.addToDriverTab("Signaled Object",
            selectedSignaledObject.toString());

    private static void setSignaledObject(SignaledObject signaledObject) {
        System.out.println("SETTING SIGNALED OBJECT TO " + signaledObject);
        selectedSignaledObject = signaledObject;
        signaledObjectEntry.setString(signaledObject.toString());

    }

    public static SignaledObject getSignaledObject() {
        return selectedSignaledObject;
    }
}
