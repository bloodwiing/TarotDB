package me.bloodwiing.tarotdb.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import me.bloodwiing.tarotdb.builders.InspectBuilder;
import me.bloodwiing.tarotdb.converters.DeterministicColor;
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

    @FXML
    private Pane paneBg;

    @FXML
    private Pane paneBgGradient;

    @FXML
    private FlowPane hboxKeywords;

    @Override
    public void settingUpdate() {
        imgImage.setImage(tarot.getImageResource());
        remakeBackground();

        imgImage.getScene().getRoot().setStyle("-accent: " + DeterministicColor.colorToHSB(SettingsManager.getInstance().getAccentColor()) + ";");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgImage.sceneProperty().addListener((observableValue, scene, t1) -> {
            tarot = (Tarot) observableValue.getValue().getRoot().getUserData();

            imgImage.setImage(tarot.getImageResource());
            labelName.setText(tarot.getDisplayName());

            for (String keyword : tarot.getKeywords()) {
                Label label = new Label(keyword);
                label.getStyleClass().addAll("tag", "axis");
                label.setStyle("-color: hsb(" + DeterministicColor.hueFromObject(keyword) + ", 80%, 100%);");
                hboxKeywords.getChildren().add(label);
            }

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

            remakeBackground();
        });
    }

    private void remakeBackground() {
        BackgroundImage bgImage = new BackgroundImage(
                tarot.getImageResource(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0.5, true, Side.TOP, 0.5, true),
                new BackgroundSize(1.0, 1.0, true, true, false, true));

        paneBg.setBackground(new Background(bgImage));

        BackgroundFill bgFill = new BackgroundFill(
                new LinearGradient(0.5, 0.0, 0.5, 1.0, true, CycleMethod.NO_CYCLE, new Stop(0.0, Color.web("12121500")), new Stop(1.0, Color.web("121215FF"))),
                new CornerRadii(16), Insets.EMPTY);

        paneBgGradient.setBackground(new Background(bgFill));
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);

        imgImage.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            SettingsManager.getInstance().removeSettingUpdateListener(this);
        });
    }
}
