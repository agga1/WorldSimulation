package visualization;

import configuration.Config;
import javafx.scene.layout.GridPane;
import map.WorldMap;
import mapElements.Grass;
import mapElements.animal.Animal;
import utils.Vector2d;

import java.util.HashMap;
import java.util.List;

public class WorldView implements UpdateListener {
    private final HashMap<Vector2d, Tile> nodes = new HashMap<>();
    private GridPane grid;
    private WorldMap worldMap;
    private double tileWidth, tileHeight;

    public WorldView(GridPane grid, double gridWidth, double gridHeight) {
        final var config = Config.getInstance();
        final var params = config.params;
        this.grid = grid;
        this.tileWidth = gridWidth / params.width;
        this.tileHeight = gridHeight / params.height;
        this.worldMap = new WorldMap(this); // TODO pass ocntroller
        worldMap.getRect().toVectors().forEach(this::addTile);
    }
    private void addTile(Vector2d position){
        Tile tile = new Tile(tileWidth, tileHeight);
        tile.updateTile(Icon.FIELD, "empty", 0);
        grid.add(tile, position.x, position.y, 1, 1);
        nodes.put(position, tile);
    }
    public void update(int step){
        this.worldMap.onUpdate(step);
    }
    @Override
    public void onUpdate(List<Vector2d> updated) {
        updated.forEach(this::draw);
    }

    @Override
    public void onTileUpdate(Vector2d position, Object obj) {
        final var node = nodes.get(position);
        if(obj == null) node.updateTile(Icon.FIELD, "empty", 0);
        else if(obj instanceof Animal){
            double hue = (double) ((Animal) obj).getEnergy()/worldMap.getMaxEnergy();
            node.updateTile(Icon.ANIMAL, obj.toString(), hue);
        }
        else if(obj instanceof Grass)
            node.updateTile(Icon.GRASS, obj.toString(), 0);
    }

    private void draw(Vector2d position) {
        final var node = nodes.get(position);
        Object obj = worldMap.objectAt(position);
        if(obj == null) node.updateTile(Icon.FIELD, "empty", 0);
        else if(obj instanceof Animal){
            double hue = (double) ((Animal) obj).getEnergy()/worldMap.getMaxEnergy();
            node.updateTile(Icon.ANIMAL, obj.toString(), hue);
        }
        else if(obj instanceof Grass)
            node.updateTile(Icon.GRASS, obj.toString(), 0);
    }
}

