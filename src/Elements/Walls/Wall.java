package Elements.Walls;

import Collision.Polygon;
import Collision.Type;
import Elements.Object;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.media.opengl.GL.GL_TEXTURE_2D;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Abstract object that represents a wall
 */
abstract class Wall extends Object {
    public float Xmin;
    public float Xmax;
    public float Ymin;
    public float Ymax;
    public float Zmin;
    public float Zmax;
    float espaceTexture;
    Texture texture;
    int wall;

    /**
     * Constructor for a wall
     */
    Wall(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax) {
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

    /**
     * Define the texture for the wall
     * @param Filename the file of the texture
     * @param espaceTexture the number of time we want to see the texture
     */
    public void defineTexture(String Filename, float espaceTexture) {
        try {
            texture = TextureIO.newTexture(new File("texture/"+ Filename ),true);
        }
        catch (GLException | IOException e) {
            e.printStackTrace();
        }
        this.espaceTexture = espaceTexture;
    }

    /**
     * display the wall
     * @param gl opengl parameter
     */
    @Override
    public void display(GL2 gl) {
        gl.glPushMatrix();
        gl.glCallList(wall);
        gl.glPopMatrix();
    }
}
