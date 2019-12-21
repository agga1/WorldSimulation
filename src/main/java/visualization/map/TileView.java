package visualization.map;

import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import mapElements.ILivingMapElement;
import mapElements.IMapElement;
import mapElements.animal.Animal;
import visualization.Icon;

class TileView extends ImageView {
    private Tooltip tooltip;
    private IMapElement item;
    private MapView parent;

    TileView(MapView parent, double prefWidth, double prefHeight) {
        this.parent = parent;
        tooltip = new Tooltip("Empty");
        this.setFitWidth(prefWidth);
        this.setFitHeight(prefHeight);
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");
        Tooltip.install(this, tooltip);

        this.setOnMouseClicked(e -> setAnimalTracking());
    }

    void updateTile(IMapElement mapElement) {
        this.setEffect(null);
        this.item = mapElement;
        if (mapElement == null) {
            setImage(Icon.FIELD.img);
            tooltip.setText("empty");
            return;
        }
        setImage(mapElement.getIcon().img);
        if (mapElement instanceof ILivingMapElement) {
            if (!((ILivingMapElement) mapElement).isTracked()) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setHue(((ILivingMapElement) mapElement).getEnergy() * 0.05);
                this.setEffect(colorAdjust);
            }
        }
        tooltip.setText(mapElement.toString());
    }

    private void setAnimalTracking() {
        if (this.item instanceof Animal) {
            this.parent.setTracking((Animal) this.item);
        }
    }

    void onHighlight() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.9);
        this.setEffect(colorAdjust);
    }


}

