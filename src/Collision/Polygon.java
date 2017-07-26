package Collision;

import Elements.Object;
import com.sun.javafx.geom.Vec3d;

import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
public class Polygon extends ObjectCollision{

    public Polygon(ArrayList<Vec3d> vertices) {
        this.vertices = vertices;
    }


    @Override
    public Impact impactCollision(Object object) {
        return Impact.CONTINUE;
    }
}
