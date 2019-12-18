package visualization.map;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.WorldMap;

import java.util.stream.Collector;

public class MapViewPane extends VBox {
    private MapView mapView;
    private StatsView statsView;

    public MapViewPane(WorldMap worldMap, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        this.mapView = new MapView(worldMap, prefWidth, prefHeight*9/10);
        this.statsView = new StatsView(worldMap, prefWidth, prefHeight*1/10);

//        statsView.prefWidthProperty().bind(this.getPrefWidth());
//        statsView.prefHeightProperty().bind(this.heightProperty().divide(10).multiply(2));

//        mapView.setPadding(new Insets(0));
//        Color borderColor = Color.rgb(57, 212, 54);
//        setBorder(new Border(new BorderStroke(borderColor,borderColor, borderColor, borderColor,
//                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
//                CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY)));

        this.getChildren().add(mapView);
        System.out.println(getWidth());
    }
}
