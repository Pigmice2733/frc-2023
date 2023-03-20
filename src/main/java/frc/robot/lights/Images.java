package frc.robot.lights;

import static frc.robot.lights.Colors.*;

import java.util.ArrayList;
import java.util.Arrays;

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
                                        { 0, 0, 0, 0, 12, 0, 12, 0, 0, 0, 12, 12, 0, 0, 0, 0 },
                                        { 12, 0, 0, 12, 12, 12, 12, 12, 0, 12, 12, 12, 12, 0, 0, 12 },
                                        { 0, 12, 0, 0, 12, 12, 0, 0, 0, 0, 0, 12, 12, 0, 12, 0 },
                                        { 0, 0, 0, 12, 12, 12, 0, 12, 0, 12, 0, 12, 12, 0, 0, 0 },
                                        { 12, 12, 0, 0, 12, 12, 0, 12, 0, 12, 0, 12, 12, 0, 12, 12 },
                                        { 0, 0, 0, 12, 0, 12, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                        { 0, 12, 12, 12, 0, 12, 12, 0, 12, 12, 12, 0, 12, 12, 12, 0 },
                                        { 0, 0, 0, 12, 0, 0, 12, 0, 0, 0, 12, 0, 0, 0, 12, 0 },
                                        { 0, 12, 12, 12, 0, 0, 12, 0, 0, 12, 12, 0, 0, 12, 12, 0 },
                                        { 0, 12, 0, 0, 0, 0, 12, 0, 0, 0, 12, 0, 0, 0, 12, 0 },
                                        { 0, 12, 12, 12, 0, 0, 12, 0, 12, 12, 12, 0, 12, 12, 12, 0 },
                                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } });

        // public static Image OWEN_PIGMICE = new Image(new byte[][] {
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        // { B, 0, PURPLE, PURPLE, 0, 0, 0, 0, 0, 0, 0, PURPLE, PURPLE, 0, B, 0 },
        // { 0, PURPLE, 0, 0, PURPLE, 0, 0, 0, 0, 0, PURPLE, 0, 0, PURPLE, 0, 0 },
        // { PURPLE, 0, B, 0, PURPLE, PURPLE, 0, 0, 0, PURPLE, PURPLE, 0, B, 0, PURPLE,
        // 0 },
        // { PURPLE, 0, 0, B, 0, 0, PURPLE, PURPLE, PURPLE, 0, 0, B, 0, 0, PURPLE, 0 },
        // { PURPLE, B, B, 0, B, PURPLE, 0, 0, 0, PURPLE, B, 0, B, B, PURPLE, 0 },
        // { PURPLE, 0, 0, B, B, PURPLE, 0, 0, 0, PURPLE, B, B, 0, 0, PURPLE, 0 },
        // { PURPLE, 0, 0, 0, B, 0, PURPLE, PURPLE, PURPLE, 0, B, 0, 0, 0, PURPLE, 0 },
        // { 0, PURPLE, PURPLE, B, 0, PURPLE, 0, 0, 0, PURPLE, 0, B, PURPLE, PURPLE, 0,
        // 0 },
        // { 0, 0, B, PURPLE, PURPLE, 0, 0, 0, 0, 0, PURPLE, PURPLE, B, 0, 0, 0 },
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

        public static final Image CASCADIA_FLAG = new Image(new byte[][] {
        {11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11 },
        {11,11,11,11,11,11,11,0,0,11,11,11,11,11,11,11 }, 
        {11,11,11,11,11,11,0,0,0,0,11,11,11,11,11,11 },
        {11,11,11,11,11,11,11,0,0,11,11,11,11,11,11,11 },
        {11,11,11,11,11,0,11,0,0,11,0,11,11,11,11,11 },
        {1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1 },
        {1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1 },
        {1,1,1,0,0,1,0,0,0,0,1,0,0,1,1,1 },
        {1,1,1,1,1,0,1,0,0,1,0,1,1,1,1,1 },
        {1,1,0,0,0,0,1,0,0,1,0,0,0,0,1,1 },
        {1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1 },
        {7,7,7,0,0,0,0,0,0,0,0,0,0,7,7,7 },
        {7,7,0,7,0,0,7,0,0,7,0,0,7,0,7,7 },
        {7,7,7,7,7,7,0,0,0,0,7,7,7,7,7,7 },
        {7,7,7,7,7,7,7,0,0,7,7,7,7,7,7,7 },
        {7,7,7,7,7,7,7,0,0,7,7,7,7,7,7,7}
                });
}
