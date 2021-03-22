package cz.educanet.game;

import cz.educanet.game.models.Square;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class Game {

    public static int rowCount;
    public static int columnCount;
    public static float squareWidth;
    public static float squareHeight;

    public static ArrayList<Square> squareArrayList = new ArrayList<Square>();
    

    public static float[] vertices2 = {
            1.0f, 0.0f, 0.0f, // 0 -> Top right
            1.0f, -1.0f, 0.0f, // 1 -> Bottom right
            0.0f, -1.0f, 0.0f, // 2 -> Bottom left
            0.0f, 0.0f, 0.0f, // 3 -> Top left
    };

    public static String maze1 = "10111\n" +
            "10001\n" +
            "11101\n" +
            "01001\n" +
            "11111\n";

    public static void init(long window) {
        // Setup shaders
        Shaders.initShaders();
    }

    public static void render(long window) {

      renderSquares();

    }

    public static void update(long window) {

    }

    // go through the arrayList of squares and draw each one
    private static void renderSquares() {
        // for each : maze1 draw square
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
        rowCount = rows.length;

        columnCount = rows[0].length();

        squareWidth = 2f / rowCount;
        squareHeight = 2f / columnCount;
    }

    // call this before initial game loop - saves all squares to a arrayList that can then be rendered
    public static void createDrawableSquaresList() {
        // calculates the x and y range for the square which fills the maze
        calculateMazeSize();

        for (int y = 0; y < columnCount; y++) {
            for (int x = 0; x < rowCount; x++) {
                // if check for if char is 0 or 1
                if (maze1.charAt(x + y*x) == 0) {

                    // create new instance of square
                    Square square = new Square();

                    // take the top left point (-1;1), add side size, add position based on amount of previous squares on x and y
                    float[] newVertices = {
                            -1.0f + squareWidth + (2.0f/rowCount * x), 1.0f                + (2.0f/rowCount * y), 0.0f, // 0 -> Top right
                            -1.0f + squareWidth + (2.0f/rowCount * x), 1.0f + squareHeight + (2.0f/rowCount * y), 0.0f, // 1 -> Bottom right
                            -1.0f               + (2.0f/rowCount * x), 1.0f + squareHeight + (2.0f/rowCount * y), 0.0f, // 2 -> Bottom left
                            -1.0f               + (2.0f/rowCount * x), 1.0f                + (2.0f/rowCount * y), 0.0f, // 3 -> Top left
                    };
                    // set positions of all 4 points
                    square.setVertices(newVertices);

                    // add square to arrayList
                    squareArrayList.add(square);
                }
            }
        }
    }
}
