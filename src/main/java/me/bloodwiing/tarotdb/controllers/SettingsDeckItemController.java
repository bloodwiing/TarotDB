package me.bloodwiing.tarotdb.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import me.bloodwiing.tarotdb.data.Deck;

public class SettingsDeckItemController {

    private Deck deck;

    @FXML
    private ImageView imgImage;

    @FXML
    private Label lblArtist;

    @FXML
    private RadioButton radioOption;

    public void setData(Deck deck) {
        this.deck = deck;

        imgImage.setImage(new Image(deck.getDefaultCard()));
        radioOption.setText(deck.name());
        lblArtist.setText("by " + deck.artist());
    }

    public void onImageClicked(MouseEvent event) {
        if (event.isConsumed()) {
            return;
        }

        if (event.getEventType() != MouseEvent.MOUSE_CLICKED || event.getButton() != MouseButton.PRIMARY) {
            return;
        }

        radioOption.fire();

        event.consume();
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        radioOption.setOnAction(handler);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setRadioSelected(boolean value) {
        radioOption.setSelected(value);
    }
}
