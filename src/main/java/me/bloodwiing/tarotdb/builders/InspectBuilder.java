package me.bloodwiing.tarotdb.builders;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import me.bloodwiing.tarotdb.TarotDB;
import me.bloodwiing.tarotdb.controllers.InspectRowController;

import java.io.IOException;

public class InspectBuilder {
    private final VBox content;

    public InspectBuilder(VBox content) {
        this.content = content;
    }

    public InspectRowController addParagraph(String name) {
        FXMLLoader rowLoader = new FXMLLoader(TarotDB.class.getResource("inspect-row-view.fxml"));
        Parent row;
        try {
            row = rowLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        content.getChildren().add(row);

        InspectRowController rowController = rowLoader.getController();

        rowController.setName(name);

        return rowController;
    }

    public static Label createWrappingLabel(String text) {
        Label result = new Label(text);
        result.setWrapText(true);
        return result;
    }
}
