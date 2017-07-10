package Elements.Walls;

import Collision.Type;
import Elements.Object;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
abstract class Wall extends Object {
    public float Xmin;
    public float Xmax;
    public float Ymin;
    public float Ymax;
    public float Zmin;
    public float Zmax;
    float espaceTexture;
    float[] colors;
    Texture texture;
    int wall;

    public Wall(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax) {
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.Zmin = Zmin;
        this.Zmax = Zmax;
        vertices = new ArrayList<>();
        verticesNormal = new ArrayList<>();
        collisionType = Type.POLYGON;
    }
    public void defineTexture(String Filename, float espaceTexture) {
        try {
            texture = TextureIO.newTexture(new File("texture/"+ Filename ),true);
        }
        catch (GLException | IOException e) {
            e.printStackTrace();
        }
        this.espaceTexture = espaceTexture;
    }

    public void display(GL2 gl) {
        gl.glPushMatrix();
        gl.glCallList(wall);
        gl.glPopMatrix();
    }
}
