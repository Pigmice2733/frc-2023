package frc.robot.lights;

import java.util.HashMap;
import java.util.Map;

public class Text {
        private static Map<Character, byte[][]> characters = new HashMap<Character, byte[][]>();

        public static enum TextScrollDirection {
                LEFT, RIGHT
        }

        public static class TextSequence {
                private byte[][][] letters;
                private int length;

                public TextSequence(String text) {
                        letters = new byte[text.length()][][];
                        for (int i = 0; i < text.length(); i++) {
                                letters[i] = characters.get(text.toUpperCase().charAt(i));
                        }

                        for (byte[][] letter : letters) {
                                length += letter[0].length + 1;
                        }

                        length -= 1; // accounts for the extra space at the end of the text
                }

                public byte[][][] getLetters() {
                        return letters;
                }

                public byte[][] getLetter(int index) {
                        return letters[index];
                }

                public int getLength() {
                        return length;
                }

                public int getLetterCount() {
                        return letters.length;
                }

                public int getLetterWidth(int index) {
                        return letters[index][0].length;
                }

                public int getLetterHeight(int index) {
                        return letters[index].length;
                }

                public TextSequence setColor(byte color) {
                        for (byte[][] letter : letters) {
                                for (int y = 0; y < letter.length; y++) {
                                        for (int x = 0; x < letter[y].length; x++) {
                                                if (letter[y][x] > 0)
                                                        letter[y][x] = color;
                                        }
                                }
                        }
                        return this;
                }
        }

        public static TextSequence buildLetters(String text) {
                return new TextSequence(text);
        }

        static {
                characters.put('A', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                });
                characters.put('B', new byte[][] {
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 0 },
                });
                characters.put('C', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 0, 1, 1, 1 },
                });
                characters.put('D', new byte[][] {
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 0 }
                });
                characters.put('E', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 1 }
                });
                characters.put('F', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 }
                });
                characters.put('G', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 1 }
                });
                characters.put('H', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 }
                });
                characters.put('I', new byte[][] {
                                { 1, 1, 1 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 1, 1, 1 }
                });
                characters.put('J', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 0, 0, 1, 0 },
                                { 0, 0, 1, 0 },
                                { 0, 0, 1, 0 },
                                { 1, 0, 1, 0 },
                                { 0, 1, 0, 0 }
                });
                characters.put('K', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 1, 0 },
                                { 1, 1, 0, 0 },
                                { 1, 0, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 }
                });
                characters.put('L', new byte[][] {
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 1 }
                });
                characters.put('M', new byte[][] {
                                { 1, 1, 0, 1, 1 },
                                { 1, 0, 1, 0, 1 },
                                { 1, 0, 1, 0, 1 },
                                { 1, 0, 0, 0, 1 },
                                { 1, 0, 0, 0, 1 },
                                { 1, 0, 0, 0, 1 }
                });
                characters.put('N', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 1, 0, 1 },
                                { 1, 0, 1, 1 },
                                { 1, 0, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 }
                });
                characters.put('O', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('P', new byte[][] {
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 0 }
                });
                characters.put('Q', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 1, 1 },
                                { 0, 1, 1, 1 }
                });
                characters.put('R', new byte[][] {
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 }
                });
                characters.put('S', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 0, 0, 1 },
                                { 0, 0, 0, 1 },
                                { 1, 1, 1, 0 }
                });
                characters.put('T', new byte[][] {
                                { 1, 1, 1 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 }
                });
                characters.put('U', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('V', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 1, 0 }
                });
                characters.put('W', new byte[][] {
                                { 1, 0, 0, 0, 1 },
                                { 1, 0, 0, 0, 1 },
                                { 1, 0, 0, 0, 1 },
                                { 1, 0, 1, 0, 1 },
                                { 1, 0, 1, 0, 1 },
                                { 0, 1, 0, 1, 0 }
                });
                characters.put('X', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 }
                });
                characters.put('Y', new byte[][] {
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 1, 0 }
                });
                characters.put('Z', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 0, 0, 0, 1 },
                                { 0, 0, 1, 0 },
                                { 0, 1, 0, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 1 }
                });
                characters.put('1', new byte[][] {
                                { 0, 1, 0 },
                                { 1, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 0, 1, 0 },
                                { 1, 1, 1 }
                });
                characters.put('2', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 0, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 1 }
                });
                characters.put('3', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 0, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 0, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('4', new byte[][] {
                                { 0, 0, 1, 0 },
                                { 0, 1, 1, 0 },
                                { 1, 0, 1, 0 },
                                { 1, 1, 1, 1 },
                                { 0, 0, 1, 0 },
                                { 0, 0, 1, 0 }
                });
                characters.put('5', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 0 },
                                { 0, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('6', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('7', new byte[][] {
                                { 1, 1, 1, 1 },
                                { 0, 0, 0, 1 },
                                { 0, 0, 1, 0 },
                                { 0, 1, 0, 0 },
                                { 0, 1, 0, 0 },
                                { 0, 1, 0, 0 }
                });
                characters.put('8', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('9', new byte[][] {
                                { 0, 1, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 1 },
                                { 0, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('0', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 1, 1 },
                                { 1, 1, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 0, 1, 1, 0 }
                });
                characters.put('!', new byte[][] {
                                { 1 },
                                { 1 },
                                { 1 },
                                { 1 },
                                { 0 },
                                { 1 }
                });
                characters.put('?', new byte[][] {
                                { 0, 1, 1, 0 },
                                { 1, 0, 0, 1 },
                                { 0, 0, 1, 0 },
                                { 0, 1, 0, 0 },
                                { 0, 0, 0, 0 },
                                { 0, 1, 0, 0 }
                });
                characters.put(' ', new byte[][] {
                                { 0, 0, 0, 0 },
                                { 0, 0, 0, 0 },
                                { 0, 0, 0, 0 },
                                { 0, 0, 0, 0 },
                                { 0, 0, 0, 0 },
                                { 0, 0, 0, 0 },
                });
        }
}
