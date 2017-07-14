package Elements;

import Collision.Type;

import javax.media.opengl.GL2;

/**
 * Adele Bendayan
 * 336141056
 */
public class Plane extends ObjectTextured {
    public Plane(String file) {
        super(new float[]{0.0f, 0.0f, 0.0f}, new float[]{0.05f, 0.05f, 0.05f}, new float[]{-90.0f, 1.0f, 0.0f, 1.0f}, file, Type.BOX);
    }

    public void displayPlane(GL2 gl, float[] position) {
        translate = position;
        display(gl);
    }
}
