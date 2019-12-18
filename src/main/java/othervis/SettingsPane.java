package othervis;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import visualization.SimulationStatus;

public class SettingsPane extends VBox {
    public SettingsPane(SimulationStatus simulationStatus) {
        this.setAlignment(Pos.CENTER);
        Button pauseButton = new Button();
        pauseButton.setText(simulationStatus.running ? "pause" : "start");
        pauseButton.setOnAction(event -> {
            simulationStatus.running = !simulationStatus.running;
            pauseButton.setText(simulationStatus.running ? "pause" : "start");
        });
        getChildren().add(pauseButton);

        Slider intervalSlider = new Slider(100, 1000, simulationStatus.interval);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = t1.longValue());
        getChildren().add(intervalSlider);
    }
}
