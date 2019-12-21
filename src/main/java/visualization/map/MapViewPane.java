package visualization.map;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.WorldMap;
import mapElements.animal.Animal;
import mapElements.animal.Genome;
import utils.Vector2d;

import java.util.List;
import java.util.Set;

public class MapViewPane extends VBox implements UpdateListener {
    private MapView mapView;
    private StatsView statsView;
    private WorldMap worldMap;
    private StatsView animalStats;
    private Button showGenome;


    public MapViewPane(WorldMap worldMap, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;
        worldMap.setViewController(this);
        showGenome = new Button();
        showGenome.setText("show dominant genome");
        showGenome.setOnAction(actionEvent -> onShowDominantGenome());

        this.statsView = new StatsView(prefWidth, 0, "worldstats.txt");
        this.animalStats = new StatsView(0, 0, "animalstats.txt");
        HBox allStats = new HBox(statsView, animalStats);
        allStats.setAlignment(Pos.TOP_LEFT);

        this.mapView = new MapView(this, worldMap, prefWidth, prefHeight * 8.5 / 10);

        this.getChildren().addAll(mapView, showGenome, allStats);
    }

    @Override
    public void onUpdate(Set<Vector2d> updated) {
        mapView.onUpdate(updated);  // TODO pass objects here
        statsView.onUpdate(worldMap.getStats());
        onShowAnimalStats(worldMap.getTracked());
    }

    private void onShowDominantGenome() {
        Genome dominantGenome = worldMap.getStats().getDominantGenome();
        List<Animal> animals = this.worldMap.getAnimalsWithGenome(dominantGenome);
        this.mapView.highlightAnimals(animals);
    }

    void onShowAnimalStats(Animal animal) {
        if (animal != null)
            this.animalStats.onUpdate(animal.getStats());
        else
            this.animalStats.onUpdate(null);
    }
}
