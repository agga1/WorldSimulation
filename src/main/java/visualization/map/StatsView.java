package visualization.map;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import map.WorldMap;


public class StatsView extends VBox {
    private Label day;
    private WorldMap worldMap;
    public StatsView(WorldMap worldMap, double prefWidth, double prefHeight){
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);
        this.worldMap = worldMap;
        day = new Label("0");
        this.getChildren().add(day);
    }

    public void unUpdate(){
        day.setText(String.valueOf(worldMap.getDay()));
    }


}
