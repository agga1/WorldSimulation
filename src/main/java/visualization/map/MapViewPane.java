package visualization.map;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import map.WorldMap;

public class MapViewPane extends VBox {
    private MapView mapView;

    public MapViewPane(WorldMap worldMap) {
        this.mapView = new MapView(worldMap);
        mapView.prefWidthProperty().bind(this.widthProperty());
        mapView.prefHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        mapView.minWidthProperty().bind(this.widthProperty());
        mapView.minHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        mapView.maxWidthProperty().bind(this.widthProperty());
        mapView.maxHeightProperty().bind(this.heightProperty().divide(10).multiply(9));

        mapView.setPadding(new Insets(0));

        setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY)));

        this.getChildren().add(mapView);
    }
}
