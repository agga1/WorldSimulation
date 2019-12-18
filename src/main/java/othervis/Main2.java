package othervis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import map.WorldMap;
import visualization.*;

import static visualization.ViewConfig.WINDOW_HEIGHT;
import static visualization.ViewConfig.WINDOW_WIDTH;

public class Main2 extends Application {
    private SimulationStatus simulationStatus;
    private SimulationsViewPane SimulationsViewPane;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.simulationStatus = new SimulationStatus();
        SimulationsViewPane = new SimulationsViewPane(simulationStatus, 2);
    }

    private void update() {

    }

}