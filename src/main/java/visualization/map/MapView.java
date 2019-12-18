package visualization.map;

import configuration.WorldConfig;
import javafx.scene.layout.GridPane;
import map.WorldMap;
import mapElements.IMapElement;
import utils.Vector2d;

import java.util.HashMap;
import java.util.List;

public class MapView extends GridPane implements UpdateListener {
    private WorldMap worldMap;
    private HashMap<Vector2d, TileView> nodes = new HashMap<>();

    public MapView(WorldMap worldMap){
        this.worldMap = worldMap;
        worldMap.setViewController(this);
        worldMap.getRect().toVectors().forEach(this::addTile);
    }

    private void addTile(Vector2d position){
        TileView tile = new TileView();
        tile.fitWidthProperty().bind(this.widthProperty()
                .subtract(this.getPadding().getLeft()+this.getPadding().getRight())
                .divide(WorldConfig.getInstance().params.width));
        tile.fitHeightProperty().bind(this.heightProperty()
                .subtract(this.getPadding().getTop()+this.getPadding().getBottom())
                .divide(WorldConfig.getInstance().params.width));

        tile.updateTile(null);
        this.add(tile, position.x, position.y, 1, 1);
        nodes.put(position, tile);
    }

    @Override
    public void onUpdate(List<Vector2d> updated) {
        updated.forEach(pos->onTileUpdate(pos, worldMap.objectAt(pos)));
    }

    @Override
    public void onTileUpdate(Vector2d position, IMapElement mapElement) {
        TileView node = nodes.get(position);
        node.updateTile(mapElement);

    }
}
