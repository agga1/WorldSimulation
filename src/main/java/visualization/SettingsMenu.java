package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class SettingsMenu extends ToolBar {
    SimulationState state;

    public SettingsMenu(){
        this.state = SimulationState.getInstance();
        Button start = new Button("start");
        start.setOnAction(event -> System.out.println("Starting!"));
        Button stop = new Button("stop");
        stop.setOnAction(event -> System.out.println("stopping!"));
        Button fasterStep= new Button("stepx10");
//        start.setOnAction(event -> System.out.println("quicker step!"));
        fasterStep.setOnAction(actionEvent -> {state.step *= 10;});
        Button slowerStep = new Button("step/10");
        slowerStep.setOnAction(actionEvent -> {if(state.step>=10)  state.step /= 10;});
        start.setOnAction(event -> System.out.println("slower step!"));
//        super(start);
        getItems().addAll(start, stop, fasterStep, slowerStep);
    }

}
