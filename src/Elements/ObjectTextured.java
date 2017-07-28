package Elements;

import Collision.AABB;
import Collision.BoundingSphere;
import Collision.Type;
import Collision.Impact;
import Utilities.Parser;

import javax.media.opengl.GL2;
import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Object created from a .obj file
 */
public class ObjectTextured extends Object {

    private String file;

    /**
     * Constructor
     * @param translate the position of the object
     * @param scale the scale
     * @param rotate the rotation to give
     * @param file the file where is define the object
     * @param collisionType the type of the collision
     * @param impact the impact
     */
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
    }

    /**
     * make the object
     * @param gl opengl parameter
     */
    @Override
    public void makeObject(GL2 gl) {
        Parser parser = new Parser("texture/" + file);
        object = parser.getList(gl); // get the list from the parser
        vertices = new ArrayList<>(parser.getVertices());
        verticesNormal = new ArrayList<>(parser.getNormal());
        switch (collisionType) {
            // create the collision object according to the type of the collision
            case SPHERE:
                collisionModel = new BoundingSphere(vertices, translate, scale);
                break;
            case BOX:
                collisionModel = new AABB(vertices, translate, scale, rotate);
                break;
        }
    }
}
