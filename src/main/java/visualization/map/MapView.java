package visualization.map;

import configuration.WorldConfig;
import javafx.scene.layout.GridPane;
import map.WorldMap;
import mapElements.IMapElement;
import mapElements.animal.Animal;
import utils.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.lang.Double.min;

public class MapView extends GridPane {
    private WorldMap worldMap;
    private HashMap<Vector2d, TileView> nodes = new HashMap<>();
    private MapViewPane parent;

    public MapView(MapViewPane parent, WorldMap worldMap, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;
        this.parent = parent;

        worldMap.getRect().toVectors().forEach(this::addTile);
        this.setHgap(1);
        this.setVgap(1);
    }

    private void addTile(Vector2d position) {
        double edge = min(this.getPrefWidth() / WorldConfig.getInstance().params.width - 2,
                this.getPrefHeight() / WorldConfig.getInstance().params.height - 2);
        TileView tile = new TileView(this, edge, edge);

        tile.updateTile(null);
        this.add(tile, position.x, position.y, 1, 1);
        nodes.put(position, tile);
    }

    void onUpdate(Set<Vector2d> updated) {
        updated.forEach(pos -> onTileUpdate(pos, worldMap.objectAt(pos)));
    }

    private void onTileUpdate(Vector2d position, IMapElement mapElement) {
        TileView node = nodes.get(position);
        node.updateTile(mapElement);

    }

    void setTracking(Animal animal) {
        this.worldMap.onTrackingChanged(animal, !animal.isTracked());
        onTileUpdate(animal.getPosition(), animal);
        if (animal.isTracked())
            this.parent.onShowAnimalStats(animal);
        else this.parent.onShowAnimalStats(null);
    }

    void highlightAnimals(List<Animal> animals) {
        animals.stream().map(Animal::getPosition).map(pos -> nodes.get(pos)).forEach(TileView::onHighlight);
    }
}
