package Elements;

import Collision.Impact;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL2;

/**
 * Adele Bendayan
 * 336141056
 */
public class Building extends Object {
    int building;
    float[] scale;
    float[] rotate;
    Texture texture;

    public Building(float[] translate, float[] scale, float[] rotate, GL2 gl) {
        String Filename = "texture/building.jpg";
        this.translate = translate;
        this.scale = scale;
        this.rotate = rotate;
        try {
            texture = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeObject(gl);
        impact = Impact.DEAD;
    }

    @Override
    public void makeObject(GL2 gl) {
        building = gl.glGenLists(1);
        gl.glNewList(building, GL2.GL_COMPILE);
        gl.glPushMatrix();

        texture.bind(gl);
        texture.enable(gl);
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

    @SuppressWarnings("Duplicates")
    @Override
    public void display(GL2 gl) {
        if(!holding){
            gl.glPushMatrix();
            gl.glTranslatef(translate[0], translate[1], translate[2]);
            gl.glRotatef(rotate[0], rotate[1], rotate[2], rotate[3]);
            gl.glScalef(scale[0], scale[1], scale[2]);
            gl.glCallList(building);
            gl.glPopMatrix();
        }
    }
}
