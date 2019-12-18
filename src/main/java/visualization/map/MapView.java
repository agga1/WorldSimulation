package visualization.map;

import configuration.WorldConfig;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.WorldMap;
import mapElements.IMapElement;
import utils.Vector2d;

import java.io.ObjectInputFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.lang.Double.min;

public class MapView extends GridPane {
    private WorldMap worldMap;
    private HashMap<Vector2d, TileView> nodes = new HashMap<>();

    public MapView(WorldMap worldMap, double prefWidth, double prefHeight){
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;

        worldMap.getRect().toVectors().forEach(this::addTile);
        this.setHgap(1);
        this.setVgap(1);
        Color borderColor = Color.rgb(157, 112, 54);
    }

    private void addTile(Vector2d position){// TODO - 50
        double edge = min(this.getPrefWidth()/WorldConfig.getInstance().params.width -1,
                this.getPrefHeight()/WorldConfig.getInstance().params.height-1);
        System.out.println(edge);
        TileView tile = new TileView(edge, edge);

        tile.updateTile(null);
        this.add(tile, position.x, position.y, 1, 1);
        nodes.put(position, tile);
    }

    public void onUpdate(Set<Vector2d> updated) {
        updated.forEach(pos->onTileUpdate(pos, worldMap.objectAt(pos)));
    }

    private void onTileUpdate(Vector2d position, IMapElement mapElement) {
        TileView node = nodes.get(position);
        node.updateTile(mapElement);

    }
}
