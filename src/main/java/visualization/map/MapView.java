package visualization.map;

import configuration.WorldConfig;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
//    private TileView[][] nodes;
    private HashMap<Vector2d, TileView> nodes = new HashMap<>();
    private MapViewPane parent;

    public MapView(MapViewPane parent, WorldMap worldMap, double prefWidth, double prefHeight){
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;
        this.parent = parent;

//        this.nodes = new TileView[worldMap.getRect().width()][worldMap.getRect().height()];
        worldMap.getRect().toVectors().forEach(this::addTile);
        this.setHgap(1);
        this.setVgap(1);
        Color borderColor = Color.rgb(157, 112, 54);
    }

    private void addTile(Vector2d position){// TODO - 50
        double edge = min(this.getPrefWidth()/WorldConfig.getInstance().params.width -1,
                this.getPrefHeight()/WorldConfig.getInstance().params.height-1);
        System.out.println(edge);
        TileView tile = new TileView(this, edge, edge);

        tile.updateTile(null);
        this.add(tile, position.x, position.y, 1, 1);
        nodes.put(position, tile);
//        nodes[position.x][ position.y] = tile;
    }

    public void onUpdate(Set<Vector2d> updated) {
        if(worldMap.getTracked() != null){

        }
        updated.forEach(pos->onTileUpdate(pos, worldMap.objectAt(pos)));
    }

    private void onTileUpdate(Vector2d position, IMapElement mapElement) {
        TileView node = nodes.get(position);
//        TileView node = nodes[position.x][position.y];
        node.updateTile(mapElement);

    }

    public void setTracking(Animal animal){
        this.worldMap.onTrackingChanged(animal, !animal.isTracked());
        onTileUpdate(animal.getPosition(), animal);
        if(animal.isTracked())
            this.parent.onShowAnimalStats(animal);
        else this.parent.onHideAnimalStats();
    }

    public void highlightAnimals(List<Animal> animals){
        animals.stream().map(Animal::getPosition);
    }
}
