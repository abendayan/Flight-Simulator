package Collision;

import Elements.Object;
import com.jogamp.opengl.math.VectorUtil;
import com.sun.javafx.geom.Vec3d;

import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Class that represent a collision object of Bounding sphere type
 */
public class BoundingSphere extends ObjectCollision {

    public BoundingSphere(ArrayList<Vec3d> vertices, float[] translate, float[] scale) {
        double x = 0, y = 0, z = 0;
        for(Vec3d vertice : vertices) {
            vertice.x *= scale[0];
            vertice.y *= scale[1];
            vertice.z *= scale[2];

            x += vertice.x;
            y += vertice.y;
            z += vertice.z;
        }
        x /= vertices.size();
        y /= vertices.size();
        z /= vertices.size();
        center = new Vec3d(x, y, z);
        R = 0;
        float temp = 0;
        float[] centerF = new float[] {(float)center.x, (float)center.y, (float)center.z};
        for(Vec3d vertice: vertices) {
            temp = VectorUtil.distVec3(centerF, new float[] {(float)vertice.x, (float)vertice.y, (float)vertice.z});
            if(temp > R) {
                R = temp;
            }
        }
        center.x += translate[0];
        center.y += translate[1];
        center.z += translate[2];
    }

    @Override
    public Impact impactCollision(Object object) {
        return Impact.CONTINUE;
    }
}
