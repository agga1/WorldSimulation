package mapElements.grass;

import mapElements.IMapElement;
import utils.Vector2d;
import visualization.Icon;

public class Grass implements IMapElement {
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Icon getIcon() {
        return Icon.GRASS;
    }

    public String toString() {
        return "Grass at " + position;
    }
}
