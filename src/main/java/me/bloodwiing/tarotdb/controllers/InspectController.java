package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import me.bloodwiing.tarotdb.builders.InspectBuilder;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import me.bloodwiing.tarotdb.managers.SettingsManager;

import java.net.URL;
import java.util.ResourceBundle;

import static me.bloodwiing.tarotdb.builders.InspectBuilder.createWrappingLabel;

public class InspectController implements Initializable, SettingUpdateListener {

    private Tarot tarot;

    @FXML
    private VBox vboxContent;

    @FXML
    private ImageView imgImage;

    @FXML
    private Label labelName;

    @FXML
    private VBox vboxFortunes;

    @FXML
    private VBox vboxLight;

    @FXML
    private VBox vboxShadow;

    @Override
    public void settingUpdate() {
        imgImage.setImage(tarot.getImageResource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgImage.sceneProperty().addListener((observableValue, scene, t1) -> {
            tarot = (Tarot) observableValue.getValue().getRoot().getUserData();

            imgImage.setImage(tarot.getImageResource());
            labelName.setText(tarot.getDisplayName());

            for (String fortune : tarot.getFortuneTellings()) {
                vboxFortunes.getChildren().add(createWrappingLabel(fortune));
            }

            for (String light : tarot.getLightMeanings()) {
                vboxLight.getChildren().add(createWrappingLabel(light));
            }

            for (String shadow : tarot.getShadowMeanings()) {
                vboxShadow.getChildren().add(createWrappingLabel(shadow));
            }

            tarot.buildInfo(new InspectBuilder(vboxContent));
        });
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);

        imgImage.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            SettingsManager.getInstance().removeSettingUpdateListener(this);
        });
    }
}
