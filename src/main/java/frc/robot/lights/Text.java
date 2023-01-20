package frc.robot.lights;

import java.util.HashMap;
import java.util.Map;

public class Text {
    private static Map<Character, int[][]> characters = new HashMap<Character, int[][]>();

    public static enum TextScrollDirection {
        LEFT, RIGHT
    }

    public static class TextSequence {
        private int[][][] letters;
        private int length;

        public TextSequence(String text) {
            int[][][] letters = new int[text.length()][][];
            for (int i = 0; i < text.length(); i++) {
                letters[i] = characters.get(text.toUpperCase().charAt(i));
            }

            for (int[][] letter : letters) {
                length += letter[0].length + 1;
            }

            length -= 1; // accounts for the extra space at the end of the text
        }

        public int[][][] getLetters() {
            return letters;
        }

        public int[][] getLetter(int index) {
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

        public TextSequence setColor(int color) {
            for (int[][] letter : letters) {
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
        characters.put('A', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
        });
        characters.put('B', new int[][] {
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 0 },
        });
        characters.put('C', new int[][] {
                { 0, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 0, 1, 1, 1 },
        });
        characters.put('D', new int[][] {
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 0 }
        });
        characters.put('E', new int[][] {
                { 1, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 1 }
        });
        characters.put('F', new int[][] {
                { 1, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 }
        });
        characters.put('G', new int[][] {
                { 0, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 1, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 1 }
        });
        characters.put('H', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 }
        });
        characters.put('I', new int[][] {
                { 1, 1, 1 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 1, 1, 1 }
        });
        characters.put('J', new int[][] {
                { 0, 1, 1, 1 },
                { 0, 0, 1, 0 },
                { 0, 0, 1, 0 },
                { 0, 0, 1, 0 },
                { 1, 0, 1, 0 },
                { 0, 1, 0, 0 }
        });
        characters.put('K', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 1, 0 },
                { 1, 1, 0, 0 },
                { 1, 0, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 }
        });
        characters.put('L', new int[][] {
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 1 }
        });
        characters.put('M', new int[][] {
                { 1, 1, 0, 1, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 }
        });
        characters.put('N', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 1, 0, 1 },
                { 1, 0, 1, 1 },
                { 1, 0, 1, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 }
        });
        characters.put('O', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('P', new int[][] {
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 0, 0, 0 }
        });
        characters.put('Q', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 1, 1 },
                { 0, 1, 1, 1 }
        });
        characters.put('R', new int[][] {
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 1, 1, 0 },
                { 1, 0, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 }
        });
        characters.put('S', new int[][] {
                { 0, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
                { 0, 0, 0, 1 },
                { 1, 1, 1, 0 }
        });
        characters.put('T', new int[][] {
                { 1, 1, 1 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 }
        });
        characters.put('U', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('V', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 }
        });
        characters.put('W', new int[][] {
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 0, 0, 1 },
                { 1, 0, 1, 0, 1 },
                { 1, 0, 1, 0, 1 },
                { 0, 1, 0, 1, 0 }
        });
        characters.put('X', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 }
        });
        characters.put('Y', new int[][] {
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 },
                { 0, 1, 1, 0 }
        });
        characters.put('Z', new int[][] {
                { 1, 1, 1, 1 },
                { 0, 0, 0, 1 },
                { 0, 0, 1, 0 },
                { 0, 1, 0, 0 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 1 }
        });
        characters.put('1', new int[][] {
                { 0, 1, 0 },
                { 1, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 1, 1, 1 }
        });
        characters.put('2', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 0, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 1 }
        });
        characters.put('3', new int[][] {
                { 1, 1, 1, 1 },
                { 0, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 0, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('4', new int[][] {
                { 0, 0, 1, 0 },
                { 0, 1, 1, 0 },
                { 1, 0, 1, 0 },
                { 1, 1, 1, 1 },
                { 0, 0, 1, 0 },
                { 0, 0, 1, 0 }
        });
        characters.put('5', new int[][] {
                { 1, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 0 },
                { 0, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('6', new int[][] {
                { 0, 1, 1, 1 },
                { 1, 0, 0, 0 },
                { 1, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('7', new int[][] {
                { 1, 1, 1, 1 },
                { 0, 0, 0, 1 },
                { 0, 0, 1, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 0, 0 },
                { 0, 1, 0, 0 }
        });
        characters.put('8', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('9', new int[][] {
                { 0, 1, 1, 1 },
                { 1, 0, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 1 },
                { 0, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('0', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 1, 0, 1, 1 },
                { 1, 1, 0, 1 },
                { 1, 0, 0, 1 },
                { 0, 1, 1, 0 }
        });
        characters.put('!', new int[][] {
                { 1 },
                { 1 },
                { 1 },
                { 1 },
                { 0 },
                { 1 }
        });
        characters.put('?', new int[][] {
                { 0, 1, 1, 0 },
                { 1, 0, 0, 1 },
                { 0, 0, 1, 0 },
                { 0, 1, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 1, 0, 0 }
        });
    }
}
