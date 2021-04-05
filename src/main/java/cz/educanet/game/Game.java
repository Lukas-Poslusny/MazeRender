package cz.educanet.game;

import cz.educanet.game.models.Square;
import cz.educanet.game.models.Square2;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;

public class Game {

    public static int mazeRowCount;
    public static int mazeColumnCount;
    public static float squareSideWidth;
    public static float squareSideHeight;

    public static ArrayList<Square> squareArrayList = new ArrayList<>();
    

    public static String maze1 =
            "101111111000111011101010000001\n" +
            "100000101101101100111011111111\n" +
            "110011100111000110100010000100\n" +
            "010110000000010010101110110111\n" +
            "011100111101110110111010100001\n" +
            "100011100111001100100000111011\n" +
            "100000100010011000101111101010\n" +
            "110011101110010111111000000010\n" +
            "010110100001110000001010111010\n" +
            "111100101111001111011010101011\n" +
            "001000001000001010010110101001\n" +
            "111011101011101011110100101101\n" +
            "101010101010111000001110100111\n" +
            "100010111010000111001010100001\n" +
            "111110000011001101011010101101\n" +
            "100100111101011001110001101001\n" +
            "101100100101000011011011001001\n" +
            "011001100101111010010010001011\n" +
            "110111001110001110110110011010\n" +
            "100100111011100010101100110010\n" +
            "100100000000111010101011101011\n" +
            "101111011110001011001010001001\n" +
            "100001010011001001101011001101\n" +
            "110011011101101100101001111001\n" +
            "011010000100100110101011001001\n" +
            "001011001101100010001000011001\n" +
            "111001111001011010111011110011\n" +
            "010000100001010011100010001110\n" +
            "011110001111010000000111001000\n" +
            "110011111001011111111101111111";

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();
    }

    public static void render(long window) {
        // TODO render background square with gradient
        createBackgroundGradiantSquare();

        // render white squares which equal to number '0' in the maze string
        renderSquares();

    }

    public static void update(long window) {

    }

    // go through the arrayList of squares and draw each one
    private static void renderSquares() {
        // draw each square from list of only white squares
        for (int i = 0; i < squareArrayList.size(); i++) {
            Square s = squareArrayList.get(i);

            GL33.glUseProgram(Shaders.shaderId); // use this shader to render
            GL33.glBindVertexArray(s.getVaoId());
            GL33.glDrawElements(GL33.GL_TRIANGLES, s.getVertices().length, GL33.GL_UNSIGNED_INT, 0);
        }
    }

    // calculates the x and y range for the square which fills the maze
    public static void calculateMazeSize() {
        String[] rows = maze1.split("\n");
        mazeRowCount = rows.length;

        mazeColumnCount = rows[0].length();

        squareSideWidth = 2f / mazeRowCount;
        squareSideHeight = 2f / mazeColumnCount;
    }

    // call this before initial game loop - saves all squares to a arrayList that can then be rendered
    public static void createDrawableSquaresList() {
        // calculates the x and y range for the square which fills the maze
        calculateMazeSize();

        int counter = -1;
        String tempMaze = maze1.replace("\n", "");
        for (int y = 0; y < mazeColumnCount; y++) {
            for (int x = 0; x < mazeRowCount; x++) {
                // if check for if char is 0 or 1
                counter++;
                if (tempMaze.charAt(counter) == '0') {

                    // create new instance of square
                    Square square = new Square();

                    float sumSidesPreviousSquaresX = (2.0f/ mazeRowCount * x); // sum of X side values of all previous squares on X
                    float sumSidesPreviousSquaresY = (2.0f/ mazeColumnCount * y); // sum of y side values of all squares that are above on y

                    // take the base > top left point (-1;1), add side size, add position based on amount of previous squares on x and y
                    float[] newVertices = {
                            -1.0f + squareSideWidth + (sumSidesPreviousSquaresX), 1.0f                    - sumSidesPreviousSquaresY, 0.0f, // 0 -> Top right
                            -1.0f + squareSideWidth + (sumSidesPreviousSquaresX), 1.0f - squareSideHeight - sumSidesPreviousSquaresY, 0.0f, // 1 -> Bottom right
                            -1.0f                   + (sumSidesPreviousSquaresX), 1.0f - squareSideHeight - sumSidesPreviousSquaresY, 0.0f, // 2 -> Bottom left
                            -1.0f                   + (sumSidesPreviousSquaresX), 1.0f                    - sumSidesPreviousSquaresY, 0.0f, // 3 -> Top left
                    };

                    // set vertices created above to current instance of square
                    square.setVertices(newVertices);

                    // add square to arrayList that hold all squares for on of the values 0/1 - currently 0
                    squareArrayList.add(square);
                }
            }
        }
    }

    public static void createBackgroundGradiantSquare() {
        Square2 backgroundSquare = new Square2();


        GL33.glUseProgram(Shaders.shaderId); // use this shader to render
        GL33.glBindVertexArray(backgroundSquare.getVaoId());
        GL33.glDrawElements(GL33.GL_TRIANGLES, backgroundSquare.getVertices().length, GL33.GL_UNSIGNED_INT, 0);
    }
}
