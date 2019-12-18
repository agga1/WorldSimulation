package othervis;

import javafx.scene.layout.GridPane;
import map.WorldMap;
import utils.Vector2d;
import visualization.UpdateListener;

import java.util.List;

public class WorldStatusViewPane extends GridPane implements UpdateListener {
    private WorldMap worldMap;
    public WorldStatusViewPane(){
        worldMap = new WorldMap(this);
    }

    @Override
    public void onUpdate(List<Vector2d> updated) {

    }

    @Override
    public void onTileUpdate(Vector2d position, Object object) {

    }
}
