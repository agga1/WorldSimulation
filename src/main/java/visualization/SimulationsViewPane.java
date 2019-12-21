package visualization;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.WorldMap;
import visualization.map.MapView;
import visualization.map.MapViewPane;

import java.util.List;

public class SimulationsViewPane extends VBox {
    private HBox simulations = new HBox();
    private int nrOfSimulations;

    private int padding = 10;

    public SimulationsViewPane(SimulationStatus simulationStatus, List<WorldMap> worldMaps, double prefWidth, double prefHeight){
        setPrefHeight(prefHeight);
        System.out.println(prefWidth);
        setPrefWidth(prefWidth);
        // TODO create worldmaps in WorldViewController?
        this.nrOfSimulations = worldMaps.size();
        SettingsPane settings = new SettingsPane(simulationStatus, prefWidth, prefHeight*1/10);
        simulations.setPrefWidth(this.getPrefWidth());
        simulations.setPrefHeight(this.getPrefHeight()*9/10);

        getChildren().add(simulations);
        getChildren().add(settings);
        nrOfSimulations = worldMaps.size();
        worldMaps.forEach(w->addSimulation(w, nrOfSimulations));
//        Color borderColor = Color.rgb(7, 112, 54);
//        setBorder(new Border(new BorderStroke(borderColor,borderColor, borderColor, borderColor,
//                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
//                CornerRadii.EMPTY, BorderStroke.MEDIUM, Insets.EMPTY)));
    }
    private void addSimulation(WorldMap worldMap, int nrOfSimulations) {  // TODO worldmap only in worldview?
        MapViewPane map = new MapViewPane(worldMap, simulations.getPrefWidth()/nrOfSimulations, simulations.getPrefHeight());

        simulations.getChildren().add(map);
    }

}
