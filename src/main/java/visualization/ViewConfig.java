package visualization;
import javafx.scene.image.Image;

import static configuration.ResourceParser.parseImage;

public final class ViewConfig {

    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 600;

    public static final String TITLE = "Game Of Life";

    public static Image icon() {
        return parseImage("cat.jpg");
    }

}
