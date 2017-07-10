package Collision;
import Elements.Object;

/**
 * Adele Bendayan
 * 336141056
 */
public class Point extends ObjectCollision {

    public Point(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
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
                    return impactOfCollision(object);
                }
                break;
            case BOX:
                if(collisionDetection.pointAAB(this, collisionModel.Bmin, collisionModel.Bmax)) {
                    return impactOfCollision(object);
                }
                break;
        }
        return Impact.CONTINUE;
    }

    private Impact impactOfCollision(Object object) {
        if(object.impact == Impact.EXIT) {
            // TODO calculate angle
            return Impact.EXIT;
        }
        return object.impact;
    }
}

