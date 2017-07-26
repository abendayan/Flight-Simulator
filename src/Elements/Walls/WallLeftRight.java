package Elements.Walls;

import Collision.Polygon;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;

import java.util.ArrayList;

import static javax.media.opengl.GL.GL_TEXTURE_2D;

/**
 * Adele Bendayan
 * 336141056
 */
public class WallLeftRight extends Wall {
    boolean leftOrRight;
    public WallLeftRight(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax, boolean leftOrRight) {
        super(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        this.leftOrRight = leftOrRight;
    }

    @SuppressWarnings("Duplicates")
    public void makeObject(GL2 gl) {
        float X;
        if(leftOrRight) {
            X = Xmax;
        }
        else {
            X = Xmin;
        }
        wall = gl.glGenLists(1);
        gl.glNewList(wall, GL2.GL_COMPILE);
        gl.glPushMatrix();
        texture.enable(gl);
        texture.bind(gl);

        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        gl.glBegin(GL2.GL_QUADS);
        vertices = new ArrayList<>();
        gl.glTexCoord2f(0.0f, 0.0f);
        vertices.add(new Vec3d(X, Ymax, Zmin));
        gl.glVertex3f(X, Ymax, Zmin);
        gl.glTexCoord2f(0.0f, espaceTexture);
        vertices.add(new Vec3d(X, Ymin, Zmin));
        gl.glVertex3f(X, Ymin, Zmin);
        gl.glTexCoord2f(espaceTexture, espaceTexture);
        vertices.add(new Vec3d(X, Ymin, Zmax));
        gl.glVertex3f(X, Ymin, Zmax);
        gl.glTexCoord2f(espaceTexture, 0.0f);
        vertices.add(new Vec3d(X, Ymax, Zmax));
        gl.glVertex3f(X, Ymax, Zmax);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glEnd();
        gl.glEndList();
        collisionModel = new Polygon(vertices);
    }
}