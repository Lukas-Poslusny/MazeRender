package cz.educanet.game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Main {
    public static void main(String[] args) throws Exception {
        // Window init
        GLFW.glfwInit();
        // Use opengl v3.3
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        long window = GLFW.glfwCreateWindow(800, 600, "Maze", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Unable to open window");
        }
        GLFW.glfwMakeContextCurrent(window);

        // Tell GLFW that we are using OpenGL
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        //

        // Main game loop
        Game.init(window);
        Game.createDrawableSquaresList();
        Game.createBackgroundGradiantSquare();
        while (!GLFW.glfwWindowShouldClose(window)) {

            // Escape on "esc" keypress
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }


            GLFW.glfwSwapBuffers(window);   // Swap the color buffer -> screen tearing solution
            GLFW.glfwPollEvents();  // Listen to input
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT); // clear previous color


            Game.render(window);
            Game.update(window);

        }

        GLFW.glfwTerminate();
    }
}
