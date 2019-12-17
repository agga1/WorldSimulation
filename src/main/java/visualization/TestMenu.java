package visualization;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static configuration.ResourceParser.parseImage;


public class TestMenu extends ToolBar {
    public static final long MAX_INTERVAL = 150;
    public static final long MIN_INTERVAL = 50;

    private boolean running;
    private int step;

    private final Button pausePlayButton;

    public TestMenu() {

        this.running = SimulationState.getInstance().isRunning;
        this.step = SimulationState.getInstance().step;

        final var slider = new Slider(0.0, 1.0, 0.0);
//        slider.valueProperty().addListener(this::onSwiped);


        this.pausePlayButton = new Button("Pause");
        this.pausePlayButton.setGraphic(
                new ImageView(ButtonGraphics.PAUSE.image));

        this.pausePlayButton.setOnAction(this::onButtonClick);

        getItems().addAll(pausePlayButton);
    }

    private void onSwiped(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        step = (int) ((int) MIN_INTERVAL + (MAX_INTERVAL * (1 - newValue.doubleValue())));
    }

    private void onButtonClick(ActionEvent e) {
        onPausePlayEvent();
    }

    private enum ButtonGraphics {
        PLAY("play-btn.png"),
        PAUSE("pause-btn.png");

        final Image image;
        ButtonGraphics(String name) {
            this.image = parseImage(name);
        }
    }

    public void onPausePlayEvent() {
        final var wasRunning = this.running;
        this.running =!wasRunning;
        SimulationState.getInstance().isRunning = !wasRunning;

        if (wasRunning) {
            this.pausePlayButton.setText("Play");
            this.pausePlayButton.setGraphic
                    (new ImageView(ButtonGraphics.PLAY.image));

        } else {
            this.pausePlayButton.setGraphic(
                    new ImageView(ButtonGraphics.PAUSE.image));
            this.pausePlayButton.setText("Pause");
        }
    }

}
