package me.bloodwiing.tarotdb;

import javafx.application.Application;
import javafx.css.Style;
import javafx.css.Stylesheet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.bloodwiing.tarotdb.controllers.ListController;

import java.io.IOException;
import java.util.Objects;

public class TarotDB extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TarotDB.class.getResource("list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Tarot DB");
        stage.setScene(scene);
        stage.show();

        ListController controller = fxmlLoader.getController();
        controller.attachEvents();
    }
}