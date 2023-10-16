package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import me.bloodwiing.tarotdb.managers.SettingsManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ListItemController implements SettingUpdateListener {

    private Tarot tarot;

    @FXML
    private ImageView imgCard;

    @FXML
    private Label lblHead;

    @FXML
    private Label lblLabel;

    @Override
    public void settingUpdate() {
        imgCard.setImage(tarot.getImageResource());
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);
    }

    public void removeEvents() {
        SettingsManager.getInstance().removeSettingUpdateListener(this);
    }

    public void setData(Tarot tarot) throws IOException {
        this.tarot = tarot;

        imgCard.setImage(tarot.getImageResource());
        lblHead.setText(tarot.getListHead());
        lblLabel.setText(tarot.getListLabel());
    }

    public Tarot getData() {
        return tarot;
    }

}
