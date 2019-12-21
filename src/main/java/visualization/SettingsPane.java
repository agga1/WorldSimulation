package visualization;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import static java.lang.Math.min;

public class SettingsPane extends VBox {
    public SettingsPane(SimulationStatus simulationStatus, double prefWidth, double prefHeight) {
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);

        this.setAlignment(Pos.CENTER);
        List<Button> buttons = addButtons(simulationStatus);
        HBox buttonsPane = new HBox();
        buttonsPane.setAlignment(Pos.CENTER);
        buttons.forEach(b -> buttonsPane.getChildren().add(b)); // TODO unpack list?

        Slider intervalSlider = new Slider(100, 1000, simulationStatus.interval);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.setShowTickLabels(true);
        intervalSlider.setMaxWidth(200);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = t1.intValue());

        buttonsPane.getChildren().add(intervalSlider);
        buttonsPane.setSpacing(10);
        getChildren().addAll(buttonsPane);
    }

    private List<Button> addButtons(SimulationStatus simulationStatus){
        Button pauseButton = new Button();
        pauseButton.setGraphic(simulationStatus.running ? new ImageView(Icon.PLAY.img) : new ImageView(Icon.PAUSE.img));
        pauseButton.setOnAction(event -> {
            simulationStatus.running = !simulationStatus.running;
            pauseButton.setGraphic(simulationStatus.running ? new ImageView(Icon.PLAY.img) : new ImageView(Icon.PAUSE.img));
        });

        Button stepUp = new Button();
        stepUp.setText("step x 10");
        stepUp.setOnAction(actionEvent -> simulationStatus.step =min(1000, simulationStatus.step*10));
        Button stepDown = new Button();
        stepDown.setText("step / 10");
        stepDown.setOnAction(actionEvent -> simulationStatus.step = simulationStatus.step >= 10 ? simulationStatus.step/=10 : simulationStatus.step);

        List<Button> buttons = List.of(pauseButton, stepUp, stepDown);
        buttons.forEach(b -> b.setMinHeight(40));
        return buttons;
    }

}
