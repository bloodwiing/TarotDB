package me.bloodwiing.tarotdb.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.TarotDB;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.managers.TarotManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {

    private Tarot selectedTarot;

    @FXML
    private Button btnResetFilter;

    @FXML
    private FlowPane flowCardList;

    @FXML
    private ImageView imgPreview;

    @FXML
    private ChoiceBox<?> selArcana;

    @FXML
    private ChoiceBox<?> selNumber;

    @FXML
    private ChoiceBox<?> selSuit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL listItemURL = TarotDB.class.getResource("list-item-view.fxml");

        for (Tarot card : TarotManager.getInstance().getCards()) {
            FXMLLoader listItemLoader = new FXMLLoader(listItemURL);
            Parent listItem;
            try {
                listItem = listItemLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ListItemController controller = listItemLoader.getController();
            try {
                controller.setData(card);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            listItem.setOnMouseClicked(mouseEvent -> {
                onCardClick(mouseEvent, card);
            });

            flowCardList.getChildren().add(listItem);
        }
    }

    private void onCardClick(MouseEvent event, Tarot tarot) {
        if (event.isConsumed()) {
            return;
        }

        if (event.getEventType() != MouseEvent.MOUSE_CLICKED || event.getButton() != MouseButton.PRIMARY) {
            return;
        }

        if (selectedTarot != tarot) {
            selectedTarot = tarot;
            imgPreview.setImage(tarot.getImageResource());

        } else if (event.getClickCount() == 2) {
            FXMLLoader inspectLoader = new FXMLLoader(TarotDB.class.getResource("inspect-view.fxml"));
            Parent inspect;
            try {
                inspect = inspectLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            inspect.setUserData(tarot);

            Stage stage = new Stage();
            stage.setTitle("Tarot DB -- " + tarot.getName());
            stage.setScene(new Scene(inspect));
            stage.show();
        }

        event.consume();
    }

    @FXML
    public void onOpenSettings(ActionEvent actionEvent) {
        FXMLLoader settingsLoader = new FXMLLoader(Program.class.getResource("settings-view.fxml"));
        Parent settings;
        try {
            settings = settingsLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setTitle("Tarot DB Settings");
        stage.setScene(new Scene(settings));
        stage.show();
    }
}
