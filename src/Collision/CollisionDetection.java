package Collision;

import com.jogamp.opengl.math.VectorUtil;
import com.sun.javafx.geom.Vec3d;

import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
class CollisionDetection {
    /**
     * pointPolygon
     * @param point
     * @param vertices
     * @return boolean
     */
    boolean pointPolygon(Vec3d point, ArrayList<Vec3d> vertices) {
        ArrayList<Vec3d> copy = new ArrayList<>();
        for(Vec3d vertice : vertices) {
            copy.add(vertice);
        }
        copy.add(vertices.get(0));
        double angle = 0;
        for(int i = 0; i < copy.size()-1; i++) {
            angle += angleBetween2Lines(point, copy.get(i), point, copy.get(i+1));
        }
        return !(angle < 355);
    }

    boolean pointAAB(Vec3d point, Vec3d Bmin, Vec3d Bmax) {
        if(point.x >= Bmin.x && point.y >= Bmin.y && point.z >= Bmin.z) {
            if(point.x <= Bmax.x && point.y <= Bmax.y && point.z <= Bmax.z) {
                return true;
            }
        }
        return false;
    }

    boolean boundingSphereSphereIntersection(Vec3d center1, double R1, Vec3d center2, double R2) {
        double D;
        D = (center1.x - center2.x)*(center1.x - center2.x);
        D += (center1.y - center2.y)*(center1.y - center2.y);
        D += (center1.z - center2.z)*(center1.z - center2.z);
        return D < (R1 + R2) * (R1 + R2);
    }

    boolean boudingSphereIntersection(Vec3d point, Vec3d center, double R) {
        double D;
        D = VectorUtil.distVec3(new float[] {(float)point.x, (float)point.y, (float)point.z},
                new float[] {(float)center.x, (float)center.y, (float)center.z});
        return D < R * R;
    }

    boolean boundingSpherePlane(Vec3d center, double R, Vec3d P, Vec3d n) {
        Vec3d vector = new Vec3d(center.x - P.x, center.y - P.y, center.z - P.z);
        double d = vector.dot(n);
        return d < R;
    }

    boolean AABBIntersection(Vec3d center1, Vec3d Size1, Vec3d center2, Vec3d Size2) {
        Vec3d D = new Vec3d(center1.x - center2.x, center1.y - center2.y, center1.z - center2.z);
        Vec3d Size = new Vec3d((Size1.x+Size2.x)/2, (Size1.y+Size2.y)/2, (Size1.z+Size2.z)/2);
        return D.x < Size.x && D.y < Size.y && D.z < Size.z;
    }

    boolean AABBSphereIntersection(Vec3d center, double R, Vec3d Bmin, Vec3d Bmax) {
        double s, d = 0;
        if(center.x < Bmin.x) {
            s = center.x - Bmin.x;
            d += s*s;
        }
        else if(center.x > Bmax.x) {
            s = center.x - Bmax.x;
            d += s*s;
        }

        if(center.y < Bmin.y) {
            s = center.y - Bmin.y;
            d += s*s;
        }
        else if(center.y > Bmax.y) {
            s = center.y - Bmax.y;
            d += s*s;
        }

        if(center.z < Bmin.z) {
            s = center.z - Bmin.z;
            d += s*s;
        }
        else if(center.z > Bmax.z) {
            s = center.z - Bmax.z;
            d += s*s;
        }

        return d * d < R * R;
    }

    private double angleBetween2Lines(Vec3d line1Start, Vec3d line1End, Vec3d line2Start, Vec3d line2End) {
        Vec3d firstLine = new Vec3d(line1End.x - line1Start.x, line1End.y - line1Start.y,
                line1End.z - line1Start.z);
        Vec3d secondLine = new Vec3d(line2End.x - line2Start.x, line2End.y - line2Start.y,
                line2End.z - line2Start.z);
        firstLine.normalize();
        secondLine.normalize();
        double angle = Math.toDegrees(VectorUtil.angleVec3(new float[]{(float)firstLine.x, (float)firstLine.y, (float)firstLine.z},
                new float[]{(float)secondLine.x, (float)secondLine.y, (float)secondLine.z}));

        return angle;
    }
}
