package visualization;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SettingsPane extends VBox {
    public SettingsPane(SimulationStatus simulationStatus) {
        this.setAlignment(Pos.CENTER);
        Button pauseButton = new Button();
        pauseButton.setGraphic(simulationStatus.running ? new ImageView(Icon.PLAY.img) : new ImageView(Icon.PAUSE.img));
        pauseButton.setOnAction(event -> {
            simulationStatus.running = !simulationStatus.running;
            pauseButton.setGraphic(simulationStatus.running ? new ImageView(Icon.PLAY.img) : new ImageView(Icon.PAUSE.img));
        });
        getChildren().add(pauseButton);

        Slider intervalSlider = new Slider(100, 1000, simulationStatus.interval);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = t1.intValue());
        getChildren().add(intervalSlider);
    }
}
