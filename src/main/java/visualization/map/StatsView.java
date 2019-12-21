package visualization.map;

import configuration.Stats;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import map.WorldMap;
import map.WorldStats;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class StatsView extends VBox {
    private Text stats;
    private Button saveToFile;
    private String saveToFileName;

    public StatsView( double prefWidth, double prefHeight, String saveToFileName){
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);
        this.saveToFileName = saveToFileName;
        stats = new Text("");
        saveToFile = new Button("save stats");
        saveToFile.setOnMouseClicked(this::onSaveStats);
        this.setSpacing(10);
        this.getChildren().addAll(stats, saveToFile);
    }

    private void onSaveStats(MouseEvent mouseEvent) {
        try(PrintStream ps = new PrintStream(this.saveToFileName)) { ps.println(this.stats.getText()); }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void onUpdate(Stats stats){
        if(stats != null)
            this.stats.setText(stats.toString());
        else
            this.stats.setText("");
    }


}
