package utils;

public enum Orientation {
    NORTH(new Vector2d(0, 1)),
    NORTHEAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTHEAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTHWEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTHWEST(new Vector2d(-1, 1));

    private Vector2d unitVector;

    Orientation(Vector2d unitVector) {
        this.unitVector = unitVector;
    }

    public Orientation turnBy(int times) {
        return Orientation.values()[(ordinal() + times) % Orientation.values().length];
    }

    public Vector2d toUnitVector() {
        return this.unitVector;
    }
}
