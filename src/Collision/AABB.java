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
 * Class that represent a collision object of AABB type
 */
public class AABB extends ObjectCollision {

    public AABB(ArrayList<Vec3d> vertices, float[] translate, float[] scale, float[] rotate) {
        double xMax = vertices.get(0).x, yMax = vertices.get(0).y, zMax = vertices.get(0).z;
        double xMin = vertices.get(0).x, yMin = vertices.get(0).y, zMin = vertices.get(0).z;
        for(Vec3d vertice : vertices) {
            if(xMax < vertice.x) {
                xMax = vertice.x;
            }
            if(xMin > vertice.x){
                xMin = vertice.x;
            }

            if(yMax < vertice.y) {
                yMax = vertice.y;
            }
            if(yMin > vertice.y){
                yMin = vertice.y;
            }

            if(zMax < vertice.z) {
                zMax = vertice.z;
            }
            if(zMin > vertice.z){
                zMin = vertice.z;
            }
        }
        xMin *= scale[0];
        xMin += translate[0];
        xMax *= scale[0];
        xMax += translate[0];

        yMin *= scale[1];
        yMin += translate[1];
        yMax *= scale[1];
        yMax += translate[1];

        zMin *= scale[2];
        zMin += translate[2];
        zMax *= scale[2];
        zMax += translate[2];

        Bmin = new Vec3d(xMin, yMin, zMin);
        Bmax = new Vec3d(xMax, yMax, zMax);
    }
    @Override
    public Impact impactCollision(Object object) {
        return Impact.CONTINUE;
    }
}
