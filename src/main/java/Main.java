import configuration.ViewConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import map.WorldMap;
import visualization.SimulationStatus;
import visualization.SimulationsViewPane;

import java.util.List;

import static configuration.ViewConfig.*;

// TODO scale images properly
public class Main extends Application {
    private SimulationStatus simulationStatus;
    private visualization.SimulationsViewPane SimulationsViewPane;
    private List<WorldMap> worldMaps;

    private VBox vBox;
    private Button button;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);

        this.simulationStatus = new SimulationStatus();
        worldMaps = List.of(new WorldMap(), new WorldMap()); // TODO prettify
        SimulationsViewPane root = new SimulationsViewPane(simulationStatus, worldMaps, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.button = new Button();
        button.setText("start");
        this.vBox = new VBox(button);

        Thread thread = new Thread(() -> {
            Runnable runnable = this::updateSimulations;
            while (true) {
                try {
                    Thread.sleep(simulationStatus.interval);
                } catch (InterruptedException ignore) {
                }
                if (simulationStatus.running) {
                    Platform.runLater(runnable);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        stage.setScene(new Scene(root, root.getWidth(), root.getHeight()));
//        stage.setScene(new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();

    }

    private void updateSimulations() {
        worldMaps.forEach(worldMap -> worldMap.onUpdate(simulationStatus.step));
//        button.setText(String.valueOf(worldMaps.get(0).getDay()));

    }

    private void update() {

    }

}