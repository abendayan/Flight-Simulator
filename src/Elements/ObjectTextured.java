package Elements;

import Collision.AABB;
import Collision.BoundingSphere;
import Collision.Type;
import Collision.Impact;
import Utilities.Parser;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
public class ObjectTextured extends Object {
    int object;

    float[] scale;
    float[] rotate;
    String file;

    public ObjectTextured(float[] translate, float[] scale, float[] rotate, String file,
                          Type collisionType) {
        this.translate = translate;
        this.scale = scale;
        this.rotate = rotate;
        this.file = file;
        vertices = new ArrayList<>();
        verticesNormal = new ArrayList<>();
        this.collisionType = collisionType;
        holding = false;
    }


    public ObjectTextured(float[] translate, float[] scale, float[] rotate, String file,
                          Type collisionType, Impact impact) {
        this.translate = translate;
        this.scale = scale;
        this.rotate = rotate;
        this.file = file;
        vertices = new ArrayList<>();
        verticesNormal = new ArrayList<>();
        this.collisionType = collisionType;
        this.impact = impact;
        holding = false;
    }

    @Override
    public void makeObject(GL2 gl) {
        Parser parser = new Parser("texture/" + file);
        object = parser.getList(gl);
        vertices = new ArrayList<>(parser.getVertices());
        verticesNormal = new ArrayList<>(parser.getNormal());
        switch (collisionType) {
            case SPHERE:
                collisionModel = new BoundingSphere(vertices, translate, scale, rotate);
                break;
            case BOX:
                collisionModel = new AABB(vertices, translate, scale, rotate);
                break;
        }
    }

    @Override
    public void display(GL2 gl) {
        if(!holding){
            gl.glPushMatrix();
            gl.glTranslatef(translate[0], translate[1], translate[2]);
            gl.glRotatef(rotate[0], rotate[1], rotate[2], rotate[3]);
            gl.glScalef(scale[0], scale[1], scale[2]);
            gl.glCallList(object);
            gl.glPopMatrix();
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
        exist = false;
    }
}
