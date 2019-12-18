import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);

        this.simulationStatus = new SimulationStatus();
        worldMaps = List.of(new WorldMap(), new WorldMap()); // TODO prettify
        SimulationsViewPane root = new SimulationsViewPane(simulationStatus, worldMaps);
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
        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();

    }

    private void updateSimulations() {
        worldMaps.forEach(worldMap -> worldMap.onUpdate(simulationStatus.step));
    }

    private void update() {

    }

}