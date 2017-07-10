package Elements.Walls;

import Collision.Polygon;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;

import static javax.media.opengl.GL.GL_TEXTURE_2D;

/**
 * Adele Bendayan
 * 336141056
 */
public class WallFrontBack extends Wall {
    private boolean frontOrBack;
    WallFrontBack(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax, boolean frontOrBack) {
        super(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        this.frontOrBack = frontOrBack;
    }

    @SuppressWarnings("Duplicates")
    public void makeObject(GL2 gl) {
        float Z;
        if(frontOrBack) {
            Z = Zmax;
        }
        else {
            Z = Zmin;
        }
        wall = gl.glGenLists(1);
        gl.glNewList(wall, GL2.GL_COMPILE);
        gl.glPushMatrix();
        texture.enable(gl);
        texture.bind(gl);

        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);

        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0.0f, 0.0f);
        vertices.add(new Vec3d(Xmin, Ymin, Z));
        gl.glVertex3f(Xmin, Ymin, Z);
        gl.glTexCoord2f(espaceTexture, 0.0f);
        vertices.add(new Vec3d(Xmax, Ymin, Z));
        gl.glVertex3f(Xmax, Ymin, Z);
        gl.glTexCoord2f(espaceTexture, espaceTexture);
        vertices.add(new Vec3d(Xmax, Ymax, Z));
        gl.glVertex3f(Xmax, Ymax, Z);
        gl.glTexCoord2f(0.0f, espaceTexture);
        vertices.add(new Vec3d(Xmin, Ymax, Z));
        gl.glVertex3f(Xmin, Ymax, Z);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glEnd();
        gl.glEndList();
        collisionModel = new Polygon(vertices);
    }
}
