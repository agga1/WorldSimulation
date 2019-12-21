package configuration;

import utils.Rect;
import utils.Vector2d;

import static java.lang.Integer.max;

/**
 * Singleton responsible for holding information about simulation parameters.
 */
public class WorldConfig {
    private static WorldConfig instance;

    public final Params params;

    private WorldConfig() {
        this.params = ResourceParser.parseParams();
    }

    public static WorldConfig getInstance() {
        if (instance == null)
            instance = new WorldConfig();

        return instance;
    }

    public Rect jungleBounds(){
        double jungleRatio = params.jungleRatio;

        // TODO jungle can't be smaller than (1,1) ?
        Vector2d jungleDimensions = new Vector2d(
                max((int) Math.floor(params.width*jungleRatio),1),
                max((int) Math.floor(params.height*jungleRatio), 1));
        Vector2d jungleLowerLeft = new Vector2d(
                (params.width - jungleDimensions.x) / 2,
                (params.height - jungleDimensions.y) / 2);

        Vector2d jungleUpperRight = jungleLowerLeft.add(jungleDimensions)
                .subtract(new Vector2d(1, 1)); // subtract (1,1) because boundaries are inclusive
        // check if jungle within map
        Rect mapBounds = mapBounds();
        if (!mapBounds.lowerLeft.precedes(jungleLowerLeft)) {
            throw new IllegalArgumentException("Jungle lower left corner can't precede map lower left corner"+ jungleLowerLeft);
        }
        if (!mapBounds.upperRight.follows(jungleUpperRight)) {
            throw new IllegalArgumentException("Jungle upper right corner can't follow map upper right corner");
        }
        return new Rect(jungleLowerLeft, jungleUpperRight);
    }
    public Rect mapBounds(){
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(params.width-1, params.height-1);
        return new Rect(lowerLeft, upperRight);
    }
}
