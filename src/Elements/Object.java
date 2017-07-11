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
public abstract class Object {
    public Type collisionType = ORIENTEDBOX;
    public Impact impact = Impact.STOP;
    public ArrayList<Vec3d> vertices;
    public ArrayList<Vec3d> verticesNormal;
    public ObjectCollision collisionModel;
    public boolean holding;
    public boolean exist = true;
    public float[] translate;
    public abstract void makeObject(GL2 gl);
    public void destroy() {
    }
    public void display(GL2 gl) {
    }
    public void defineImpact(Impact impact) {
        this.impact = impact;
    }
}
