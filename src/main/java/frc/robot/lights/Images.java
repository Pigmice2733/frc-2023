package frc.robot.lights;

public class Images {
        public static final byte P = 13;
        public static final byte R = 2;
        // public static final byte DR = 0x7f0000;
        public static final byte B = 11;
        // public static final byte LB = 0x00ffff;
        public static final byte DB = 12;
        // public static final byte G = 0x00ff00;
        // public static final byte LG = 0x00ff80;
        // public static final byte DG = 0x008000;

        // public static Image HEART = new Image(new byte[][] {
        // { 0, R, R, 0, 0, R, R, 0 },
        // { R, 0, 0, R, R, 0, 0, R },
        // { R, 0, 0, 0, 0, 0, 0, R },
        // { 0, R, 0, 0, 0, 0, R, 0 },
        // { 0, 0, R, 0, 0, R, 0, 0 },
        // { 0, 0, 0, R, R, 0, 0, 0 }
        // });

        public static Image SMALL_HEART = new Image(new byte[][] {
                        { 0, R, 0, R, 0 },
                        { R, 0, R, 0, R },
                        { R, 0, 0, 0, R },
                        { 0, R, 0, R, 0 },
                        { 0, 0, R, 0, 0 },
        });

        // public static Image PIGMICE = new Image(new byte[][] {
        // { 0, 0, 0, P, P, 0, 0, 0, 0, 0, 0, P, P, 0, 0, 0 },
        // { 0, 0, P, 0, 0, P, 0, 0, 0, 0, P, 0, 0, P, 0, 0 },
        // { 0, P, 0, 0, 0, 0, P, 0, 0, P, 0, 0, 0, 0, P, 0 },
        // { P, 0, 0, 0, 0, P, 0, 0, 0, 0, P, 0, 0, 0, 0, P },
        // { P, 0, 0, 0, P, 0, 0, 0, 0, 0, 0, P, 0, 0, 0, P },
        // { P, 0, 0, P, 0, 0, P, 0, 0, P, 0, 0, P, 0, 0, P },
        // { 0, P, 0, P, 0, 0, P, 0, 0, P, 0, 0, P, 0, P, 0 },
        // { 0, 0, P, P, 0, 0, P, 0, 0, P, 0, 0, P, P, 0, 0 },
        // { 0, 0, 0, P, P, 0, 0, 0, 0, 0, 0, P, P, 0, 0, 0 },
        // });

        // public static Image OWEN_PIGMICE = new Image(new byte[][] {
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { B, 0, P, P, 0, 0, 0, 0, 0, 0, 0, P, P, 0, B, 0 },
        // { 0, P, 0, 0, P, 0, 0, 0, 0, 0, P, 0, 0, P, 0, 0 },
        // { P, 0, B, 0, P, P, 0, 0, 0, P, P, 0, B, 0, P, 0 },
        // { P, 0, 0, B, 0, 0, P, P, P, 0, 0, B, 0, 0, P, 0 },
        // { P, B, B, 0, B, P, 0, 0, 0, P, B, 0, B, B, P, 0 },
        // { P, 0, 0, B, B, P, 0, 0, 0, P, B, B, 0, 0, P, 0 },
        // { P, 0, 0, 0, B, 0, P, P, P, 0, B, 0, 0, 0, P, 0 },
        // { 0, P, P, B, 0, P, 0, 0, 0, P, 0, B, P, P, 0, 0 },
        // { 0, 0, B, P, P, 0, 0, 0, 0, 0, P, P, B, 0, 0, 0 },
        // { 0, B, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, B, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // });

        // public static Image CLEVELAND = new Image(new byte[][] {
        // { 0, 0, 0, 0, 0, 0, 0, 0, DG, DG, DG, DG, DG, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, DG, DG, G, LG, LG, LG, LG, DG, 0, 0 },
        // { 0, 0, 0, 0, DG, DG, LG, G, G, G, LG, LG, LG, LG, DG, 0 },
        // { 0, 0, 0, DG, LG, LG, LG, DG, DG, G, G, G, LG, LG, DG, 0 },
        // { 0, 0, DG, LG, LG, LG, LG, DG, 0, DG, DG, G, LG, LG, DG, 0 },
        // { 0, DG, G, G, LG, LG, LG, DG, 0, 0, DG, G, LG, LG, DG, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, DG, G, LG, DG, 0, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, DG, DG, DG, 0, 0, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, 0, 0, DG, DG, DG, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, DG, DG, LG, LG, DG, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, DG, G, LG, LG, DG, 0 },
        // { 0, 0, DG, G, LG, LG, LG, DG, 0, 0, DG, G, LG, LG, DG, 0 },
        // { 0, 0, DG, G, LG, LG, LG, LG, DG, DG, DG, G, G, DG, 0, 0 },
        // { 0, 0, DG, G, G, LG, LG, LG, LG, LG, LG, DG, DG, 0, 0, 0 },
        // { 0, 0, 0, DG, G, G, LG, LG, LG, LG, DG, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, DG, DG, DG, DG, DG, DG, 0, 0, 0, 0, 0, 0 },
        // });
}
