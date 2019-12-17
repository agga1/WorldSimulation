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

public class MainView extends Application {
    private WorldMap world;
//    private SettingsMenu menu;
    private ToolBar tempMenu;
    private int step=1; // TODO move to simulation config or sth
    private WorldView worldViewLeft;
    private WorldView worldViewRight;
    private StatisticsView statisticsView;

    private SimulationState state;
    private SimulationStatus simulationStatus;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.state = SimulationState.getInstance();
        this.simulationStatus = new SimulationStatus();
        final var gridLeft = new GridPane();
        final var gridRight = new GridPane();
        worldViewLeft = new WorldView(gridLeft, WINDOW_WIDTH/2, WINDOW_HEIGHT);
        worldViewRight = new WorldView(gridRight, WINDOW_WIDTH/2, WINDOW_HEIGHT);
        statisticsView = new StatisticsView();
//        this.menu = new SettingsMenu();
        this.tempMenu = setupMenu();

        final var hbox = new HBox(gridLeft, gridRight);

        final var vbox = new VBox( tempMenu, hbox);
        final var scene = new Scene(vbox);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Thread thread = new Thread(() -> {
            Runnable updater = this::update;
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignore) {}

                // UI update is run on the UI thread
                if(state.isRunning){
                    Platform.runLater(updater);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setTitle(ViewConfig.TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(ViewConfig.icon());
        stage.show();
    }

    private void update() {
        this.worldViewLeft.update(state.step);
        this.worldViewRight.update(state.step);

    }

    private ToolBar setupMenu(){

        Button pausePLayButton = new Button("");
        pausePLayButton.setGraphic(new ImageView(Icon.PLAY.img));
        Button fasterStep= new Button("stepx10");
//        start.setOnAction(event -> System.out.println("quicker step!"));
        fasterStep.setOnAction(actionEvent -> {state.step *= 10;});
        Button slowerStep = new Button("step/10");
        slowerStep.setOnAction(actionEvent -> {if(state.step>=10)  state.step /= 10;});
        pausePLayButton.setOnAction(e-> onPausePlayEvent(pausePLayButton));

        return new ToolBar(pausePLayButton, fasterStep, slowerStep);
    }

    public void onPausePlayEvent(Button pausePlayButton) {
        final var wasRunning = state.isRunning;
        state.isRunning = !wasRunning;

        if (wasRunning) {
            pausePlayButton.setText("");
            pausePlayButton.setGraphic
                    (new ImageView(Icon.PAUSE.img));

        } else {
            pausePlayButton.setGraphic(
                    new ImageView(Icon.PLAY.img));
            pausePlayButton.setText("");
        }
    }

}