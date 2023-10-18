package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.converters.DeterministicColor;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import me.bloodwiing.tarotdb.managers.SettingsManager;
import me.bloodwiing.tarotdb.managers.TarotManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TableController implements Initializable, SettingUpdateListener {

    private final List<TableCardController> cards = new ArrayList<>();

    @FXML
    private HBox hboxCards;

    @FXML
    private Label lblVersion;

    @Override
    public void settingUpdate() {
        hboxCards.getScene().getRoot().setStyle("-accent: " + DeterministicColor.colorToHSB(SettingsManager.getInstance().getAccentColor()) + ";");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblVersion.setText(SettingsManager.getInstance().getVersion());

        List<Tarot> originalList = new ArrayList<>(TarotManager.getInstance().getCards());
        Collections.shuffle(originalList);

        Collection<Tarot> choices = originalList.stream().limit(3).toList();

        for (Tarot tarot : choices) {
            addCard(tarot);
        }

        hboxCards.sceneProperty().addListener((observableValue, scene, t1) -> {
            hboxCards.getScene().getRoot().setStyle("-accent: " + DeterministicColor.colorToHSB(SettingsManager.getInstance().getAccentColor()) + ";");
        });
    }

    public void addCard(Tarot tarot) {
        var card = TableCardController.createElement();

        card.getValue().setData(tarot);

        hboxCards.getChildren().add(card.getKey());
        cards.add(card.getValue());
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);

        for (TableCardController card : cards) {
            card.attachEvents();
        }

        hboxCards.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            removeEvents();
        });
    }

    public void removeEvents() {
        SettingsManager.getInstance().removeSettingUpdateListener(this);

        for (TableCardController card : cards) {
            card.removeEvents();
        }
    }

    public static Pair<Parent, TableController> createElement() {
        FXMLLoader loader = new FXMLLoader(Program.class.getResource("table-view.fxml"));
        Parent element;
        try {
            element = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Pair<>(element, loader.getController());
    }
}
