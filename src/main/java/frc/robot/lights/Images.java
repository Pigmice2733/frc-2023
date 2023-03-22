package frc.robot.lights;

import static frc.robot.lights.Colors.*;

public class Images {

        // public static Image HEART = new Image(new byte[][] {
        // { 0, RED, RED, 0, 0, RED, RED, 0 },
        // { RED, 0, 0, RED, RED, 0, 0, R },
        // { RED, 0, 0, 0, 0, 0, 0, R },
        // { 0, RED, 0, 0, 0, 0, RED, 0 },
        // { 0, 0, RED, 0, 0, RED, 0, 0 },
        // { 0, 0, 0, RED, RED, 0, 0, 0 }
        // });

        public static Image SMALL_HEART = new Image(new byte[][] {
                        { 0, RED, 0, RED, 0 },
                        { RED, 0, RED, 0, RED },
                        { RED, 0, 0, 0, RED },
                        { 0, RED, 0, RED, 0 },
                        { 0, 0, RED, 0, 0 },
        });

        public static Image PIGMICE = new Image(new byte[][] {
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, PURPLE, PURPLE, 0, 0, 0, 0, 0, 0, PURPLE, PURPLE, 0, 0, 0 },
                        { 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0 },
                        { 0, PURPLE, 0, 0, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, 0, 0, PURPLE, 0 },
                        { PURPLE, 0, 0, 0, 0, PURPLE, 0, 0, 0, 0, PURPLE, 0, 0, 0, 0, PURPLE },
                        { PURPLE, 0, 0, 0, PURPLE, 0, 0, 0, 0, 0, 0, PURPLE, 0, 0, 0, PURPLE },
                        { PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE },
                        { 0, PURPLE, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, PURPLE, 0 },
                        { 0, 0, PURPLE, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0, PURPLE, PURPLE, 0, 0 },
                        { 0, 0, 0, PURPLE, PURPLE, 0, 0, 0, 0, 0, 0, PURPLE, PURPLE, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        });

        public static final Image IZZY_PIGMICE = new Image(
                        new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                        { 0, 0, 0, 0, 6, 0, 6, 0, 0, 0, 6, 6, 0, 0, 0, 0 },
                                        { 6, 0, 0, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 0, 0, 6 },
                                        { 0, 6, 0, 0, 6, 6, 0, 0, 0, 0, 0, 6, 6, 0, 6, 0 },
                                        { 0, 0, 0, 6, 6, 6, 0, 6, 0, 6, 0, 6, 6, 0, 0, 0 },
                                        { 6, 6, 0, 0, 6, 6, 0, 6, 0, 6, 0, 6, 6, 0, 6, 6 },
                                        { 0, 0, 0, 6, 0, 6, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                        { 0, 6, 6, 6, 0, 6, 6, 0, 6, 6, 6, 0, 6, 6, 6, 0 },
                                        { 0, 0, 0, 6, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 6, 0 },
                                        { 0, 6, 6, 6, 0, 0, 6, 0, 0, 6, 6, 0, 0, 6, 6, 0 },
                                        { 0, 6, 0, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 6, 0 },
                                        { 0, 6, 6, 6, 0, 0, 6, 0, 6, 6, 6, 0, 6, 6, 6, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } });

        public static Image OWEN_PIGMICE = new Image(new byte[][] {
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { BLUE, 0, PURPLE, PURPLE, 0, 0, 0, 0, 0, 0, 0, PURPLE, PURPLE, 0, BLUE, 0 },
                        { 0, PURPLE, 0, 0, PURPLE, 0, 0, 0, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0 },
                        { PURPLE, 0, BLUE, 0, PURPLE, PURPLE, 0, 0, 0, PURPLE, PURPLE, 0, BLUE, 0, PURPLE,
                                        0 },
                        { PURPLE, 0, 0, BLUE, 0, 0, PURPLE, PURPLE, PURPLE, 0, 0, BLUE, 0, 0, PURPLE, 0 },
                        { PURPLE, BLUE, BLUE, 0, BLUE, PURPLE, 0, 0, 0, PURPLE, BLUE, 0, BLUE, BLUE, PURPLE, 0 },
                        { PURPLE, 0, 0, BLUE, BLUE, PURPLE, 0, 0, 0, PURPLE, BLUE, BLUE, 0, 0, PURPLE, 0 },
                        { PURPLE, 0, 0, 0, BLUE, 0, PURPLE, PURPLE, PURPLE, 0, BLUE, 0, 0, 0, PURPLE, 0 },
                        { 0, PURPLE, PURPLE, BLUE, 0, PURPLE, 0, 0, 0, PURPLE, 0, BLUE, PURPLE, PURPLE, 0,
                                        0 },
                        { 0, 0, BLUE, PURPLE, PURPLE, 0, 0, 0, 0, 0, PURPLE, PURPLE, BLUE, 0, 0, 0 },
                        { 0, BLUE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, BLUE, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        });

        public static Image CLEVELAND = new Image(new byte[][] {
                        { 0, 0, 0, 0, 0, 0, 0, 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, GREEN, DARK_GREEN,
                                        0, 0 },
                        { 0, 0, 0, 0, DARK_GREEN, DARK_GREEN, GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, GREEN, GREEN,
                                        GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, 0, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN, GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN, GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN,
                                        DARK_GREEN, GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN, DARK_GREEN,
                                        GREEN, DARK_GREEN, 0, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN, 0, 0, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, 0, 0, DARK_GREEN,
                                        DARK_GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN, DARK_GREEN,
                                        GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN, DARK_GREEN,
                                        GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, DARK_GREEN, DARK_GREEN,
                                        GREEN, GREEN, DARK_GREEN, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0 },
                        { 0, 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, GREEN, GREEN, GREEN,
                                        DARK_GREEN, DARK_GREEN, 0, 0, 0 },
                        { 0, 0, 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, GREEN, GREEN, GREEN, GREEN, DARK_GREEN, 0, 0, 0,
                                        0, 0 },
                        { 0, 0, 0, 0, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0, 0,
                                        0, 0, 0 },
        });

        public static final Image CASCADIA_FLAG = new Image(new byte[][] {
                        { BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
                                        BLUE },
                        { BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, 0, 0, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE },
                        { BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, 0, 0, 0, 0, BLUE, BLUE, BLUE, BLUE, BLUE,
                                        BLUE },
                        { BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, 0, 0, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
                                        BLUE },
                        { BLUE, BLUE, BLUE, BLUE, BLUE, 0, BLUE, 0, 0, BLUE, 0, BLUE, BLUE, BLUE, BLUE,
                                        BLUE },
                        { 15, 15, 15, 15, 15, 15, 0, 0, 0, 0, 15, 15, 15, 15, 15, 15 },
                        { 15, 15, 15, 15, 15, 15, 15, 0, 0, 15, 15, 15, 15, 15, 15, 15 },
                        { 15, 15, 15, 0, 0, 15, 0, 0, 0, 0, 15, 0, 0, 15, 15, 15 },
                        { 15, 15, 15, 15, 15, 0, 15, 0, 0, 15, 0, 15, 15, 15, 15, 15 },
                        { 15, 15, 0, 0, 0, 0, 15, 0, 0, 15, 0, 0, 0, 0, 15, 15 },
                        { 15, 15, 15, 15, 15, 0, 0, 0, 0, 0, 0, 15, 15, 15, 15, 15 },
                        { DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN },
                        { DARK_GREEN, DARK_GREEN, 0, DARK_GREEN, 0, 0, DARK_GREEN, 0, 0, DARK_GREEN, 0, 0, DARK_GREEN,
                                        0, DARK_GREEN,
                                        DARK_GREEN },
                        { DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0, 0, 0,
                                        DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN },
                        { DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0,
                                        DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN },
                        { DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, 0, 0,
                                        DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN, DARK_GREEN,
                                        DARK_GREEN }
        });
}
