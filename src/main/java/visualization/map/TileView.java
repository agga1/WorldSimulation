package visualization.map;

import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import mapElements.ILivingMapElement;
import mapElements.IMapElement;
import visualization.Icon;

public class TileView extends ImageView {
    private Tooltip tooltip;

    public TileView() {
        tooltip = new Tooltip("Empty");

        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");
        Tooltip.install(this, tooltip);
    }

    public void updateTile(IMapElement mapElement) { // TODO pass map element
        this.setEffect(null);
        if(mapElement == null){
            setImage(Icon.FIELD.img);
            tooltip.setText("empty");
            return;
        }
        setImage(mapElement.getIcon().img);
        if(mapElement instanceof ILivingMapElement){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(((ILivingMapElement) mapElement).getEnergy()*0.05);
            this.setEffect(colorAdjust);
        }
        tooltip.setText(mapElement.toString());
    }
}

