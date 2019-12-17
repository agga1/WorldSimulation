package configuration;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import javafx.scene.image.Image;

import java.io.*;

public class ResourceParser {
    private static final String defaultParameters = "parameters.json";

    private static final String resources = "src/main/resources/".replace("/", File.separator);
    private static final String images = resources + "images" + File.separator;
    private static final String configuration = resources + "configuration" + File.separator;


    public static Image parseImage(String name) {
        try {
            final FileInputStream input = new FileInputStream(images + name);
            return new Image(input);
        } catch (FileNotFoundException ignore) {
            System.out.println("no image at: "+images+name);
        }
        return null;
    }

    public static Params parseParams() {
        return parseParams(defaultParameters);
    }

    public static Params parseParams(String name) {
        try{
            Gson gson = new Gson();
            return gson.fromJson(new FileReader(configuration+name), Params.class);
        }catch (IOException e){
            System.out.println("cant read the file");
        }catch (JsonParseException e){
            System.out.println("parser error");
        }
        return null;
    }

}

