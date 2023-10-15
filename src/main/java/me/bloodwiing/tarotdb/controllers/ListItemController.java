package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.data.Tarot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ListItemController {

    @FXML
    private ImageView imgCard;

    @FXML
    private Label lblHead;

    @FXML
    private Label lblLabel;

    public void setData(Tarot tarot) throws IOException {
        imgCard.setImage(tarot.getImageResource());
        lblHead.setText(tarot.getListHead());
        lblLabel.setText(tarot.getListLabel());
    }

}
