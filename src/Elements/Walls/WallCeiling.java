package Elements.Walls;


/**
 * Adele Bendayan
 * 336141056
 */
public class WallCeiling extends WallFloorCeiling {
    public WallCeiling(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin, float Zmax) {
        super(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax,  true);
    }
}
