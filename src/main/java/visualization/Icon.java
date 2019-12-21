package visualization;

import javafx.scene.image.Image;

import static configuration.ResourceParser.parseImage;


public enum Icon {
    ANIMAL("animal.png"),
    ANIMALTRACKED("animalTracked.png"),
    ANIMALHIGHLIGHT("animalHighlight.png"),
    GRASS("weed.png"),
    FIELD("field.png"),
    PLAY("pause-btn.png"),
    PAUSE("play-btn.png");
    public final Image img;
    Icon(String name) {
        this.img = parseImage(name);
    }

}
