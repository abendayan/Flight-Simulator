package Elements;

import Collision.AABB;
import Collision.Impact;
import Collision.Type;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.media.opengl.GL.GL_TEXTURE_2D;

/**
 * Adele Bendayan
 * 336141056
 */
public class Runway extends Object {
    private Texture texture;

    public Runway(float[] translate, GL2 gl) {
        String Filename = "texture/runway.png";
        this.translate = translate;
        this.scale = new float[] {20.0f, 2.0f, 20.0f};
        this.rotate = new float[] {0,0,0,0};
        try {
            texture = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        impact = Impact.EXIT;
        makeObject(gl);
        collisionType = Type.BOX;
        vertices = new ArrayList<>();

        // front
        vertices.add(new Vec3d(0.0f, 1.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 1.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 1.0f, 1.0f));
        vertices.add(new Vec3d(0.0f, 1.0f, 1.0f));

        // back
        vertices.add(new Vec3d(0.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 1.0f));
        vertices.add(new Vec3d(0.0f, 0.0f, 1.0f));

        // right
        vertices.add(new Vec3d(1.0f, 1.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 1.0f));
        vertices.add(new Vec3d(1.0f, 1.0f, 1.0f));

        // left
        vertices.add(new Vec3d(0.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(0.0f, 1.0f, 0.0f));
        vertices.add(new Vec3d(0.0f, 1.0f, 1.0f));
        vertices.add(new Vec3d(0.0f, 0.0f, 1.0f));

        // Bottom
        vertices.add(new Vec3d(0.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 0.0f));
        vertices.add(new Vec3d(1.0f, 1.0f, 0.0f));
        vertices.add(new Vec3d(0.0f, 1.0f, 0.0f));

        // Top
        vertices.add(new Vec3d(0.0f, 0.0f, 1.0f));
        vertices.add(new Vec3d(1.0f, 0.0f, 1.0f));
        vertices.add(new Vec3d(1.0f, 1.0f, 1.0f));
        vertices.add(new Vec3d(0.0f, 1.0f, 1.0f));
        collisionModel = new AABB(vertices, translate, scale, rotate);
    }

    /**
     * make the building
     * @param gl opengl
     */
    @Override
    public void makeObject(GL2 gl) {
        object = gl.glGenLists(1);
        gl.glNewList(object, GL2.GL_COMPILE);
        gl.glPushMatrix();

        texture.bind(gl);
        texture.enable(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_QUADS);

        // front
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f( 1.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 1.0f);

        // Back
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 1.0f);

        // Right
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);

        // Left
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(0.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 1.0f);

        // Bottom
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 0.0f, 0.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 0.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0.0f, 1.0f, 0.0f);

        // Top
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0.0f, 1.0f, 1.0f);

        gl.glEnd();
        gl.glPopMatrix();
        gl.glEndList();
    }
}
