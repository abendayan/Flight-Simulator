package Collision;

import com.sun.javafx.geom.Vec3d;
import Elements.Object;

import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Represent a collision object
 */
public abstract class ObjectCollision extends Vec3d {
    CollisionDetection collisionDetection = new CollisionDetection();
    public Vec3d center;
    double R;
    ArrayList<Vec3d> vertices;
    Vec3d Bmin;
    Vec3d Bmax;
    public abstract Impact impactCollision(Object object);
}
