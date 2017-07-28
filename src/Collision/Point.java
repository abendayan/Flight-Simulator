package Collision;
import Elements.Object;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Point: represent the position of the player
 */
public class Point extends ObjectCollision {
    public Integer life;

    public Point(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
        life = 100;
    }

    public void addScale(Point vector, float scale) {
        x += vector.x * scale;
        y += vector.y * scale;
        z += vector.z * scale;
    }

    public void removeScale(Point vector, float scale) {
        x -= vector.x * scale;
        y -= vector.y * scale;
        z -= vector.z * scale;
    }

    /**
     * For an object, check if the point got an impact and return the type
     * @param object the object to check
     * @return what the impact wasxs
     */
    @Override
    public Impact impactCollision(Object object) {
        ObjectCollision collisionModel = object.collisionModel;
        switch (object.collisionType) {
            case POLYGON:
                if(collisionDetection.pointPolygon(this, collisionModel.vertices)) {
                    return object.impact;
                }
                break;
            case SPHERE:
                if(collisionDetection.boudingSphereIntersection(this, collisionModel.center, collisionModel.R)) {
                    return object.impact;
                }
                break;
            case BOX:
                if(collisionDetection.pointAAB(this, collisionModel.Bmin, collisionModel.Bmax)) {
                    return object.impact;
                }
                break;
        }
        return Impact.CONTINUE;
    }
}

