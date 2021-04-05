package cz.educanet.game.models;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square2 {

    public float[] vertices = {
            1.0f,  1.0f, 0.0f, // 0 -> Top right
            1.0f, -1.0f, 0.0f, // 1 -> Bottom right
            -1.0f, -1.0f, 0.0f, // 2 -> Bottom left
            -1.0f,  1.0f, 0.0f, // 3 -> Top left
            0.0f,  0.0f, 0.0f
    };
    public int[] indices = {
            0, 1, 4, // Right triangle
            0, 3, 4, // Top triangle
            3, 2, 4, // Third triangle
            2, 1, 4  // Fourth triangle
    };
    float[] color = {
            0.8f, 0.8f, 0.8f,
            0.8f, 0.8f, 0.8f,
            0.8f, 0.8f, 0.8f,
            0.8f, 0.8f, 0.8f,
            0.0f, 0.0f, 0.0f,
    };


    public int vaoId;
    private int vboId;
    private int eboId;
    private int colorId;


    public Square2() {
        vaoId = GL33.glGenVertexArrays();
        vboId = GL33.glGenBuffers();
        eboId = GL33.glGenBuffers();
        colorId = GL33.glGenBuffers();

        // tell OpenGL we are currently writing into this buffer (eboId)
        GL33.glBindVertexArray(vaoId);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, eboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);


        // tell OpenGL we are currently writing into this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0,3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);


        // tell OpenGL we are currently writing into this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(color.length)
                .put(color)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1,3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(cb);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVboId() {
        return vboId;
    }

    public int getEboId() {
        return eboId;
    }

    public int getColorId() {
        return colorId;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getColor() {
        return color;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;

        GL33.glBindVertexArray(vaoId);

        // tell OpenGL we are currently writing into this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0,4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
    }

    public void setColor(float[] backgroundColor) {
        this.color = backgroundColor;

        GL33.glBindVertexArray(vaoId);

        // tell OpenGL we are currently writing into this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(backgroundColor.length)
                .put(backgroundColor)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1,4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

    }

}
