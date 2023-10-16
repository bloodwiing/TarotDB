package me.bloodwiing.tarotdb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.data.Deck;
import me.bloodwiing.tarotdb.managers.SettingsManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    private Deck selectedDeck;

    private final List<SettingsDeckItemController> deckItems = new ArrayList<>();

    @FXML
    private HBox hboxDecks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedDeck = SettingsManager.getInstance().getActiveDeck();

        for (Deck deck : SettingsManager.getInstance().getDecks()) {
            FXMLLoader deckItemLoader = new FXMLLoader(Program.class.getResource("settings-deck-item-view.fxml"));
            Parent deckItem;
            try {
                deckItem = deckItemLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            SettingsDeckItemController deckItemController = deckItemLoader.getController();

            deckItemController.setData(deck);

            deckItemController.setOnAction(actionEvent -> {
                selectedDeck = deck;
                refreshDeckItems();
            });

            deckItems.add(deckItemController);

            hboxDecks.getChildren().add(deckItem);
        }

        refreshDeckItems();
    }

    private void refreshDeckItems() {
        for (SettingsDeckItemController deckItem : deckItems) {
            deckItem.setRadioSelected(deckItem.getDeck() == selectedDeck);
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        ((Stage)hboxDecks.getScene().getWindow()).close();
    }

    public void onSave(ActionEvent actionEvent) {
        SettingsManager.getInstance().setActiveDeck(selectedDeck);
        onCancel(actionEvent);
    }
}
