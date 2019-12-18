package visualization.map;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import map.WorldMap;
import map.WorldStats;


public class StatsView extends VBox {
    private Text stats;

    public StatsView( double prefWidth, double prefHeight){
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);
        stats = new Text("");
        this.getChildren().add(stats);
    }

    void onUpdate(WorldStats worldStats){
        stats.setText(worldStats.toString());
    }


}
