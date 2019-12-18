package visualization;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.WorldMap;
import visualization.map.MapView;
import visualization.map.MapViewPane;

import java.util.List;

public class SimulationsViewPane extends VBox {
    private HBox simulations = new HBox();
    private int nrOfSimulations;

    public SimulationsViewPane(SimulationStatus simulationStatus, List<WorldMap> worldMaps){
        // TODO create worldmaps in WorldViewController?
        this.nrOfSimulations = worldMaps.size();
        SettingsPane settings = new SettingsPane(simulationStatus);

        simulations.prefWidthProperty().bind(this.widthProperty());
        simulations.prefHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        simulations.minWidthProperty().bind(this.widthProperty());
        simulations.minHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        simulations.maxWidthProperty().bind(this.widthProperty());
        simulations.maxHeightProperty().bind(this.heightProperty().divide(10).multiply(9));

        settings.prefWidthProperty().bind(this.widthProperty());
        settings.prefHeightProperty().bind(this.heightProperty().divide(10));
        settings.minWidthProperty().bind(this.widthProperty());
        settings.minHeightProperty().bind(this.heightProperty().divide(10));
        settings.maxWidthProperty().bind(this.widthProperty());
        settings.maxHeightProperty().bind(this.heightProperty().divide(10));

        getChildren().add(simulations);
        getChildren().add(settings);

        worldMaps.forEach(this::addSimulation);
    }
    private void addSimulation(WorldMap worldMap) {  // TODO worldmap only in worldview?
        MapViewPane map = new MapViewPane(worldMap);

        map.prefWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.prefHeightProperty().bind(simulations.heightProperty());
        map.minWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.minHeightProperty().bind(simulations.heightProperty());
        map.maxWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.maxHeightProperty().bind(simulations.heightProperty());

        simulations.getChildren().add(map);
    }

}
