package Elements;

import Collision.BoundingSphere;
import Collision.Impact;
import Collision.Type;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
public class Ball extends Object  {
    private float[] direction;

    /**
     * Constructor for a ball
     * @param start the start of the ball
     * @param plane the plane
     * @param gl opengl
     */
    public Ball(float[] start, float[] plane, GL2 gl) {
        translate = start;
        impact = Impact.HURT;
        collisionType = Type.SPHERE;
        calculateStep(start, plane);
        vertices = new ArrayList<>();
        makeObject(gl);
        collisionModel = new BoundingSphere(vertices, translate, new float[]{1.0f, 1.0f, 1.0f});
    }

    /**
     * Calculate the direction to take
     * @param start the start
     * @param plane the objective
     */
    private void calculateStep(float[] start, float[] plane) {
        direction = new float[] { plane[0] - start[0], plane[1] - start[1], plane[2] - start[2] };
        float norm = direction[0]*direction[0] + direction[1]*direction[1] + direction[2]*direction[2];
        norm = (float)Math.sqrt((double)norm);
        norm *= 10.0f;
        direction[0] /= norm;
        direction[1] /= norm;
        direction[2] /= norm;
    }

    /**
     * make the ball
     * @param gl opengl
     */
    @Override
    public void makeObject(GL2 gl) {
        float a, b;
        float da = 18.0f, db = 18.0f;
        float radius = 1.0f;
        float x, y, z;

        object = gl.glGenLists(1);

        gl.glNewList(object, GL2.GL_COMPILE);

        for (a = -90.0f; a + da <= 90.0; a += da) {
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (b = 0.0f; b <= 360.0; b += db) {

                x = radius * COS(b) * COS(a);
                y = radius * SIN(b) * COS(a);
                z = radius * SIN(a);
                vertices.add(new Vec3d(x, y, z));
                gl.glVertex3f(x, y, z);

                x = radius * COS(b) * COS(a + da);
                y = radius * SIN(b) * COS(a + da);
                z = radius * SIN(a + da);
                vertices.add(new Vec3d(x, y, z));
                gl.glVertex3f(x, y, z);
            }
            gl.glEnd();

        }

        gl.glEndList();
    }

    private float SIN(float x) {
        return (float)java.lang.Math.sin(x *3.14159/180);
    }

    private float COS(float x) {
        return (float)java.lang.Math.cos(x *3.14159/180);
    }

    /**
     * Move the ball into the direction
     */
    private void moveBall() {
        collisionModel.center = new Vec3d(translate[0], translate[1], translate[2]);
        translate[0] += direction[0];
        translate[1] += direction[1];
        translate[2] += direction[2];
    }

    /**
     * display the ball
     * @param gl opengl parameter
     */
    @Override
    public void display(GL2 gl) {
        moveBall();
        gl.glPushMatrix();
        gl.glTranslatef(translate[0], translate[1], translate[2]);
        gl.glCallList(object);
        gl.glPopMatrix();
    }
}
