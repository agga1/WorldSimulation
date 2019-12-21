package visualization.map;

import javafx.scene.layout.*;
import javafx.scene.text.Text;
import map.WorldMap;
import mapElements.animal.Animal;
import mapElements.animal.Genome;
import utils.Vector2d;

import javax.swing.text.html.ListView;
import java.util.List;
import java.util.Set;

public class MapViewPane extends VBox implements UpdateListener{
    private MapView mapView;
    private StatsView statsView;
    private WorldMap worldMap;
    private StatsView animalStats;

    public MapViewPane(WorldMap worldMap, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.worldMap = worldMap;
        worldMap.setViewController(this);

        this.statsView = new StatsView( prefWidth, prefHeight*1/10, "worldstats.txt");
        this.animalStats = new StatsView(0, prefHeight*1/10, "animalstats.txt");
        HBox allStats = new HBox(statsView, animalStats);

        this.mapView = new MapView(this, worldMap, prefWidth, prefHeight*9/10);

        this.getChildren().addAll(mapView, allStats);
        System.out.println(getWidth());
    }
    @Override
    public void onUpdate(Set<Vector2d> updated) {
        mapView.onUpdate(updated);  // TODO pass objects here
        statsView.onUpdate(worldMap.getStats());
        onShowAnimalStats(worldMap.getTracked());
    }
// TODO add here button to show dimnant genome
    public void onShowDominantGenome(){
        Genome dominantGenome = worldMap.getStats().getDominantGenome();
//        List<Animal> animals = this.worldMap.getAnimalsWithGenome(dominantGenome);
//        this.mapView.highlightAnimals(List<Animal> animals);
    }

    public void onShowAnimalStats(Animal animal){
        if(animal != null)
            this.animalStats.onUpdate(animal.getStats());
        else
            this.animalStats.onUpdate(null);
    }
}
