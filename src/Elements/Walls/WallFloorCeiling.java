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

/**
 * Represent or a floor wall or a ceiling wall
 */
public class WallFloorCeiling extends Wall{
    private boolean floorCeiling;
    WallFloorCeiling(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax, boolean floorCeiling) {
        super(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        this.floorCeiling = floorCeiling;
    }

    @SuppressWarnings("Duplicates")
    public void makeObject(GL2 gl) {
        float Y;
        if(floorCeiling) {
            Y = Ymax;
        }
        else {
            Y = Ymin;
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
        vertices.add(new Vec3d(Xmin, Y, Zmin));
        gl.glVertex3f(Xmin, Y, Zmin);
        gl.glTexCoord2f(espaceTexture, 0.0f);
        vertices.add(new Vec3d(Xmax, Y, Zmin));
        gl.glVertex3f( Xmax, Y, Zmin);
        gl.glTexCoord2f(espaceTexture, espaceTexture);
        vertices.add(new Vec3d(Xmax, Y, Zmax));
        gl.glVertex3f(Xmax, Y, Zmax);
        gl.glTexCoord2f(0.0f, espaceTexture);
        vertices.add(new Vec3d(Xmin, Y, Zmax));
        gl.glVertex3f(Xmin, Y, Zmax);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glEnd();
        gl.glEndList();
        collisionModel = new Polygon(vertices);
    }
}