package Utilities;

import Collision.Point;
import com.jogamp.opengl.math.VectorUtil;

/**
 * Adele Bendayan
 * 336141056
 */
public class Coordinate {
    public Point X;
    public Point Y;
    public Point Z;

    public Coordinate() {
        X = new Point(1, 0, 0);
        Y = new Point(0, 1, 0);
        Z = new Point(0, 0, 1);
    }

    public void rotateZ(float angle) {
        float[] tempx = new float[]{(float)X.x,(float) X.y,(float) X.z};
        float[] tempx2 = new float[]{(float)X.x,(float) X.y,(float) X.z};
        float[] tempY = new float[]{(float)Y.x, (float)Y.y, (float)Y.z};
        float[] tempY2 = new float[]{(float)Y.x, (float)Y.y, (float)Y.z};
        // xaxis
        VectorUtil.scaleVec3(tempx, tempx, COS(angle));
        VectorUtil.scaleVec3(tempY, tempY, (-1.0f) * SIN(angle));
        VectorUtil.addVec3(tempx, tempx, tempY);
        X = new Point(tempx[0], tempx[1], tempx[2]);

        //yaxis
        VectorUtil.scaleVec3(tempx2, tempx2, SIN(angle));
        VectorUtil.scaleVec3(tempY2, tempY2, COS(angle));
        VectorUtil.addVec3(tempY2, tempY2, tempx2);
        Y = new Point(tempY2[0], tempY2[1], tempY2[2]);
    }

    public void rotateX(float angle) {
        float[] tempY = new float[]{(float)Y.x, (float)Y.y, (float)Y.z};
        float[] tempY2 = new float[]{(float)Y.x, (float)Y.y, (float)Y.z};
        float[] tempZ = new float[]{(float)Z.x, (float)Z.y, (float)Z.z};
        float[] tempZ2 = new float[]{(float)Z.x, (float)Z.y, (float)Z.z};
        // Yaxis
        VectorUtil.scaleVec3(tempY, tempY, COS(angle));
        VectorUtil.scaleVec3(tempZ, tempZ, SIN(angle));
        VectorUtil.addVec3(tempY, tempY, tempZ);
        VectorUtil.normalizeVec3(tempY);
        Y = new Point(tempY[0], tempY[1], tempY[2]);
        // Zaxis
        VectorUtil.scaleVec3(tempY2, tempY2, (-1.0f)  * SIN(angle));
        VectorUtil.scaleVec3(tempZ2, tempZ2, COS(angle));
        VectorUtil.addVec3(tempZ2, tempZ2, tempY2);
        VectorUtil.normalizeVec3(tempZ2);
        Z = new Point(tempZ2[0], tempZ2[1], tempZ2[2]);
    }

    public void rotateY(float angle) {
        float[] tempx = new float[]{(float)X.x,(float) X.y,(float) X.z};
        float[] tempx2 = new float[]{(float)X.x,(float) X.y,(float) X.z};
        float[] tempZ = new float[]{(float)Z.x, (float)Z.y, (float)Z.z};
        float[] tempZ2 = new float[]{(float)Z.x, (float)Z.y, (float)Z.z};
        // xaxis
        VectorUtil.scaleVec3(tempx, tempx, COS(angle));
        VectorUtil.scaleVec3(tempZ, tempZ, SIN(angle));
        VectorUtil.addVec3(tempx, tempx, tempZ);
        X = new Point(tempx[0], tempx[1], tempx[2]);

        //zaxis
        VectorUtil.scaleVec3(tempx2, tempx2, ((-1.0f))*SIN(angle));
        VectorUtil.scaleVec3(tempZ2, tempZ2, COS(angle));
        VectorUtil.addVec3(tempZ2, tempZ2, tempx2);
        Z = new Point(tempZ2[0], tempZ2[1], tempZ2[2]);
    }

    float SIN(float x)
    {
        return (float)java.lang.Math.sin(x *3.14159/180);
    }

    float COS(float x)
    {
        return (float)java.lang.Math.cos(x *3.14159/180);
    }
}
