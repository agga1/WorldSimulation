package mapElements;

import utils.Vector2d;
import visualization.Icon;

public interface IMapElement {
    Vector2d getPosition();

    Icon getIcon();
}
