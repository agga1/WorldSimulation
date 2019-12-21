package visualization;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.WorldMap;
import visualization.map.MapViewPane;

import java.util.List;

public class SimulationsViewPane extends VBox {
    private HBox simulations = new HBox();
    private int nrOfSimulations;

    public SimulationsViewPane(SimulationStatus simulationStatus, List<WorldMap> worldMaps, double prefWidth, double prefHeight) {
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);
        this.nrOfSimulations = worldMaps.size();
        SettingsPane settings = new SettingsPane(simulationStatus, prefWidth, prefHeight * 1 / 10);
        simulations.setPrefWidth(this.getPrefWidth());
        simulations.setPrefHeight(this.getPrefHeight() * 9 / 10);

        getChildren().add(simulations);
        getChildren().add(settings);
        nrOfSimulations = worldMaps.size();
        worldMaps.forEach(w -> addSimulation(w, nrOfSimulations));
    }

    private void addSimulation(WorldMap worldMap, int nrOfSimulations) {
        MapViewPane map = new MapViewPane(worldMap, simulations.getPrefWidth() / nrOfSimulations, simulations.getPrefHeight());

        simulations.getChildren().add(map);
    }

}
