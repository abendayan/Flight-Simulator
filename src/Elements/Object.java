package Elements;

import Collision.Impact;
import Collision.ObjectCollision;
import Collision.Type;
import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import java.util.ArrayList;

import static Collision.Type.ORIENTEDBOX;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Class that represents an object
 */
public abstract class Object {
    public Type collisionType = ORIENTEDBOX;
    public Impact impact = Impact.STOP;
    public ArrayList<Vec3d> vertices;
    protected ArrayList<Vec3d> verticesNormal;
    public ObjectCollision collisionModel;
    float[] translate;
    float[] scale;
    float[] rotate;
    int object;
    public abstract void makeObject(GL2 gl);

    /**
     * display the object
     * @param gl opengl parameter
     */
    public void display(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(translate[0], translate[1], translate[2]);
        gl.glRotatef(rotate[0], rotate[1], rotate[2], rotate[3]);
        gl.glScalef(scale[0], scale[1], scale[2]);
        gl.glCallList(object);
        gl.glPopMatrix();
    }

    public void defineImpact(Impact impact) {
        this.impact = impact;
    }
}
