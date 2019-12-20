package visualization.map;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import map.WorldMap;
import mapElements.ILivingMapElement;
import mapElements.IMapElement;
import mapElements.animal.Animal;
import visualization.Icon;

public class TileView extends ImageView {
    private Tooltip tooltip;
    private IMapElement item;
    private MapView parent;

    public TileView(MapView parent, double prefWidth, double prefHeight) {
        this.parent = parent;
        tooltip = new Tooltip("Empty");
        this.setFitWidth(prefWidth);
        this.setFitHeight(prefHeight);
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");
        Tooltip.install(this, tooltip);

//        MenuItem setTracking = new MenuItem("Set tracking");
//        setTracking.setOnAction(e-> setAnimalTracking());
//        MenuItem showSame = new MenuItem("Set tracking");
//        setTracking.setOnAction(e-> setAnimalTracking());
//        ContextMenu contextMenu = new ContextMenu(setTracking);
//        this.setOnMouseClicked(e ->{ if(item instanceof Animal) contextMenu.show(this, e.getScreenX(), e.getScreenY());});
        this.setOnMouseClicked(e-> setAnimalTracking());
    }

    public void updateTile(IMapElement mapElement) { // TODO pass map element
        this.setEffect(null);
        this.item = mapElement;
        if(mapElement == null){
            setImage(Icon.FIELD.img);  //Icon.FIELD.img
            tooltip.setText("empty");
            return;
        }
        setImage(mapElement.getIcon().img);
        if(mapElement instanceof ILivingMapElement){
                if(!((ILivingMapElement) mapElement).isTracked()) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setHue(((ILivingMapElement) mapElement).getEnergy() * 0.05);
                    this.setEffect(colorAdjust);
                }
        }
        tooltip.setText(mapElement.toString());
    }

    private void setAnimalTracking(){
        if(this.item instanceof Animal) {
            this.parent.setTracking((Animal) this.item);
        }
//        if(this.item instanceof ILivingMapElement){
//            ILivingMapElement being = (ILivingMapElement) item;
//            if(being.isTracked()) being.untrack();
//            else being.track();
//            updateTile(item);
//        }
    }


}

