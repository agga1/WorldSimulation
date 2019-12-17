package visualization;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Tile extends Button {

    public Tile(double width, double height) {
        setPrefSize(width, height);
        setMaxSize(width, height);
        setMinSize(width, height);

        final var tooltip = new Tooltip("Empty spot");

        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        tooltip.getStyleClass().add("tile-tooltip");

        setTooltip(tooltip);
    }

    public void updateTile(Icon icon, String txt, double hue) {
        final ImageView imageView = new ImageView(icon.img);

        imageView.setFitWidth(this.getPrefWidth() * 0.8);
        imageView.setFitHeight(this.getPrefHeight() * 0.8);

        ColorAdjust colorAdjust = new ColorAdjust();
        //Setting the contrast value
//        colorAdjust.setContrast(0.4);

        //Setting the hue value
        if(hue>0)
            colorAdjust.setHue(hue);

        //Setting the brightness value
//        colorAdjust.setBrightness(hue);

        //Setting the saturation value
//        colorAdjust.setSaturation(hue);

        imageView.setEffect(colorAdjust);
        setGraphic(imageView);
        getTooltip().setText(txt);
    }
}

