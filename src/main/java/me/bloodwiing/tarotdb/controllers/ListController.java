package me.bloodwiing.tarotdb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.TarotDB;
import me.bloodwiing.tarotdb.converters.DeterministicColor;
import me.bloodwiing.tarotdb.data.MajorTarot;
import me.bloodwiing.tarotdb.data.MinorTarot;
import me.bloodwiing.tarotdb.data.Suit;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import me.bloodwiing.tarotdb.managers.SettingsManager;
import me.bloodwiing.tarotdb.managers.TarotManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListController implements Initializable, SettingUpdateListener {

    private final List<ListItemController> listItems = new ArrayList<>();

    private Tarot selectedTarot;

    @FXML
    private FlowPane flowCardList;

    @FXML
    private ImageView imgPreview;

    @FXML
    private ChoiceBox<String> selArcana;

    @FXML
    private ChoiceBox<String> selNumber;

    @FXML
    private ChoiceBox<String> selSuit;

    @FXML
    private Label lblArcana;

    @FXML
    private Label lblSuit;

    @FXML
    private Label lblNumber;

    @FXML
    private Label lblVersion;

    @Override
    public void settingUpdate() {
        imgPreview.setImage(selectedTarot.getImageResource());

        imgPreview.getScene().getRoot().setStyle("-accent: " + DeterministicColor.colorToHSB(SettingsManager.getInstance().getAccentColor()) + ";");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblVersion.setText(SettingsManager.getInstance().getVersion());

        rebuildCardGrid();

        selectedTarot = listItems.get(0).getData();
        imgPreview.setImage(selectedTarot.getImageResource());

        selArcana.getItems().addAll("Major", "Minor");
        selSuit.getItems().addAll(TarotManager.getInstance().getSuits().stream().map(Suit::name).toList());
        for (int i = 0; i < 22; i++) {
            selNumber.getItems().add(Integer.toString(i));
        }
        selNumber.getItems().addAll(MinorTarot.courts);

        selArcana.setOnAction(actionEvent -> {
            lblArcana.getStyleClass().setAll("accent");
            rebuildCardGrid();
        });

        selSuit.setOnAction(actionEvent -> {
            lblSuit.getStyleClass().setAll("accent");
            rebuildCardGrid();
        });

        selNumber.setOnAction(actionEvent -> {
            lblNumber.getStyleClass().setAll("accent");
            rebuildCardGrid();
        });
    }

    public void onResetAction(ActionEvent actionEvent) {
        selArcana.setValue(null);
        selSuit.setValue(null);
        selNumber.setValue(null);

        lblArcana.getStyleClass().setAll("faint");
        lblSuit.getStyleClass().setAll("faint");
        lblNumber.getStyleClass().setAll("faint");
    }

    private void rebuildCardGrid() {
        URL listItemURL = TarotDB.class.getResource("list-item-view.fxml");

        for (ListItemController listItem : listItems) {
            listItem.removeEvents();
        }

        flowCardList.getChildren().clear();
        listItems.clear();

        for (Tarot card : getFilteredCards()) {
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

            listItems.add(controller);
        }

        if (imgPreview.getScene() != null) {
            for (ListItemController listItem : listItems) {
                listItem.attachEvents();
            }
        }
    }

    private Collection<Tarot> getFilteredCards() {
        Stream<Tarot> tarotStream = TarotManager.getInstance().getCards().stream();

        if (selArcana.getValue() != null) {
            tarotStream = tarotStream.filter(tarot -> {
                if (selArcana.getValue().equals("Major"))
                    return tarot instanceof MajorTarot;
                return tarot instanceof MinorTarot;
            });
        }

        if (selSuit.getValue() != null) {
            tarotStream = tarotStream.filter(tarot -> {
                if (tarot instanceof MajorTarot)
                    return true;
                return ((MinorTarot) tarot).getSuit().name().equals(selSuit.getValue());
            });
        }

        if (selNumber.getValue() != null) {
            int value;
            try {
                value = Integer.parseInt(selNumber.getValue());
            } catch (NumberFormatException numberFormatException) {
                value = Arrays.asList(MinorTarot.courts).indexOf(selNumber.getValue()) + 11;
            }

            int finalValue = value;

            tarotStream = tarotStream.filter(tarot -> {
                if (tarot instanceof MajorTarot) {
                    return ((MajorTarot)tarot).getNumber() == finalValue;
                } else {
                    return ((MinorTarot)tarot).getNumber() == finalValue;
                }
            });
        }

        return tarotStream.toList();
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);

        for (ListItemController listItem : listItems) {
            listItem.attachEvents();
        }

        imgPreview.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            removeEvents();
        });
    }

    public void removeEvents() {
        SettingsManager.getInstance().removeSettingUpdateListener(this);

        for (ListItemController listItem : listItems) {
            listItem.removeEvents();
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
            var pair = InspectController.createElement();

            pair.getKey().setUserData(tarot);

            Stage stage = new Stage();
            stage.setTitle("Tarot DB -- " + tarot.getName());
            stage.setScene(new Scene(pair.getKey()));
            stage.show();

            pair.getValue().attachEvents();
        }

        event.consume();
    }

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

    public void onOpenTable(ActionEvent actionEvent) {
        var pair = TableController.createElement();

        Stage stage = new Stage();
        stage.setTitle("Tarot Table");
        stage.setScene(new Scene(pair.getKey()));
        stage.show();

        pair.getValue().attachEvents();
    }
}
