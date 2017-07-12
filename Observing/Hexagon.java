package Game.GameShapes;

import Engine.GameItem;
import Engine.Graphics.Mesh;
import Engine.Graphics.Texture;

public class Hexagon extends GameItem {

    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;

    private static final float[] positions = {
            1.0f, 0.0f, 1.0f,
            0.5f, 0.8660254f, 1.0f,
            -0.5f, 0.8660254f, 1.0f,
            -1.0f, 0.0f, 1.0f,
            -0.5f, -0.8660254f, 1.0f,
            0.5f, -0.8660254f, 1.0f,
    };
    private static final int[] indices = {
            0, 1, 5, 1, 4, 5,
            1, 2, 4, 2, 3, 4
    };
    private static final float[] coloursRED = {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
    };
    private static final float[] textCoords = {
            1.0f, 0.0f, 1.0f,
            0.5f, 0.8660254f, 1.0f,
            -0.5f, 0.8660254f, 1.0f,
            -1.0f, 0.0f, 1.0f,
            -0.5f, -0.8660254f, 1.0f,
            0.5f, -0.8660254f, 1.0f,
    };

    public Hexagon(Texture texture) {
        super();

        mesh = new Mesh(positions, textCoords, indices, texture);
    }

    public Hexagon(int color) {
        super();

        if (color == RED) {
            mesh = new Mesh(positions, coloursRED, indices);
        }
    }
}
