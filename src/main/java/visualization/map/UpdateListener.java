package visualization.map;

import mapElements.IMapElement;
import utils.Vector2d;

import java.util.List;

public interface UpdateListener {
    void onUpdate(List<Vector2d> updated);

    void onTileUpdate(Vector2d position, IMapElement mapElement);
}
