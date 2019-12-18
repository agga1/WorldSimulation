package othervis;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import visualization.SimulationStatus;

import java.util.stream.IntStream;

public class SimulationsViewPane extends VBox {
    HBox simulations = new HBox();
    private int nrOfSimulations;

    public SimulationsViewPane(SimulationStatus simulationStatus, int nrOfSimulations){
        this.nrOfSimulations = nrOfSimulations;
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

        IntStream.range(0, nrOfSimulations).forEach(i-> addSimulation());

    }
    public void addSimulation() {  // TODO worldmap only in worldview?
        WorldStatusViewPane map = new WorldStatusViewPane();

        map.prefWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.prefHeightProperty().bind(simulations.heightProperty());
        map.minWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.minHeightProperty().bind(simulations.heightProperty());
        map.maxWidthProperty().bind(simulations.widthProperty().divide(nrOfSimulations));
        map.maxHeightProperty().bind(simulations.heightProperty());

        simulations.getChildren().add(map);
    }
}
