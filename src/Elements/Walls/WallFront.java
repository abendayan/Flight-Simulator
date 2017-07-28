package Elements.Walls;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Represent a front wall
 */
public class WallFront extends WallFrontBack {
    public WallFront(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax) {
        super(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax, true);
    }
}
