package visualization.map;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


public class TileContextMenu extends ContextMenu {
    public TileContextMenu( ){
        MenuItem setTracking = new MenuItem("Set Tracking");
        setTracking.setOnAction(e -> this.getOwnerNode().notify());
        this.getItems().addAll(setTracking);
    }

}
