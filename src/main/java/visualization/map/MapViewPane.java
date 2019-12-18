package visualization.map;

import javafx.scene.layout.*;
import map.WorldMap;
import utils.Vector2d;

import java.util.Set;

public class MapViewPane extends VBox implements UpdateListener{
    private MapView mapView;
    private StatsView statsView;
    private WorldMap worldMap;

    public MapViewPane(WorldMap worldMap, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;
        worldMap.setViewController(this);
        this.statsView = new StatsView( prefWidth, prefHeight*1/10);
        this.mapView = new MapView(worldMap, prefWidth, prefHeight*9/10);

        this.getChildren().addAll(mapView, statsView);
        System.out.println(getWidth());
    }
    @Override
    public void onUpdate(Set<Vector2d> updated) {
        mapView.onUpdate(updated);  // TODO pass objects here
        statsView.onUpdate(worldMap.getStats());
    }
}
