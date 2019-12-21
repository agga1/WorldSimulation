package visualization.map;

import utils.Vector2d;

import java.util.Set;

public interface UpdateListener {
    void onUpdate(Set<Vector2d> updated);
}
