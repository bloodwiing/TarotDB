package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import me.bloodwiing.tarotdb.data.Tarot;

import java.net.URL;
import java.util.ResourceBundle;

public class InspectController implements Initializable {

    private Tarot tarot;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgImage.sceneProperty().addListener((observableValue, scene, t1) -> {
            tarot = (Tarot) observableValue.getValue().getRoot().getUserData();

            imgImage.setImage(tarot.getImageResource());
            labelName.setText(tarot.getName());

            for (String fortune : tarot.getFortuneTellings()) {
                vboxFortunes.getChildren().add(new Label(fortune));
            }

            for (String light : tarot.getLightMeanings()) {
                vboxLight.getChildren().add(new Label(light));
            }

            for (String shadow : tarot.getShadowMeanings()) {
                vboxShadow.getChildren().add(new Label(shadow));
            }
        });
    }
}