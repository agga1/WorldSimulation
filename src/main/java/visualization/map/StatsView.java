package visualization.map;

import configuration.Stats;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.PrintStream;


class StatsView extends VBox {
    private Text stats;
    private String saveToFileName;

    StatsView(double prefWidth, double prefHeight, String saveToFileName) {
        setPrefHeight(prefHeight);
        setPrefWidth(prefWidth);
        this.saveToFileName = saveToFileName;
        stats = new Text("");
        Button saveToFile = new Button("save stats");
        saveToFile.setOnMouseClicked(this::onSaveStats);
        this.setSpacing(10);
        this.getChildren().addAll(stats, saveToFile);
    }

    private void onSaveStats(MouseEvent mouseEvent) {
        try (PrintStream ps = new PrintStream(this.saveToFileName)) {
            ps.println(this.stats.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void onUpdate(Stats stats) {
        if (stats != null)
            this.stats.setText(stats.toString());
        else
            this.stats.setText("");
    }


}
