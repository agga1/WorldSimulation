package visualization.map;

import mapElements.IMapElement;
import utils.Vector2d;

import java.util.List;
import java.util.Set;

public interface UpdateListener {
    void onUpdate(Set<Vector2d> updated);
}
