package utils;

import java.util.Collection;
import java.util.LinkedList;

public class Rect {
    public Vector2d lowerLeft;
    public Vector2d upperRight;

    public Rect(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean contains(Vector2d point) {
        return point.precedes(this.upperRight) && point.follows(this.lowerLeft);
    }

    /**
     * @return all vectors lying within this rectangle
     */
    public Collection<Vector2d> toVectors() {
        final var collection = new LinkedList<Vector2d>();
        for (int x = this.lowerLeft.x; x <= this.upperRight.x; x++) {
            for (int y = this.lowerLeft.y; y <= this.upperRight.y; y++)
                collection.add(new Vector2d(x, y));
        }
        return collection;
    }

    public int width() {
        return upperRight.x - lowerLeft.x;
    }

    public int height() {
        return upperRight.y - lowerLeft.y;
    }

    public int surface() {
        return (upperRight.x - lowerLeft.x) * (upperRight.y - lowerLeft.y);
    }
}

