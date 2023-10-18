package me.bloodwiing.tarotdb.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.data.Tarot;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import me.bloodwiing.tarotdb.managers.SettingsManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableCardController implements Initializable, SettingUpdateListener {

    private Tarot tarot;

    private boolean revealed = false;

    @FXML
    private ImageView imgImage;

    @FXML
    private Label lblHead;

    @FXML
    private Label lblLabel;

    @Override
    public void settingUpdate() {
        if (!revealed) {
            imgImage.setImage(new Image(SettingsManager.getInstance().getActiveDeck().getBackCard()));
        } else {
            imgImage.setImage(tarot.getImageResource());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgImage.setImage(new Image(SettingsManager.getInstance().getActiveDeck().getBackCard()));
    }

    public void setData(Tarot tarot) {
        this.tarot = tarot;

        lblHead.setText(tarot.getListHead());
        lblLabel.setText(tarot.getListLabel());
    }

    public void attachEvents() {
        SettingsManager.getInstance().addSettingUpdateListener(this);
    }

    public void removeEvents() {
        SettingsManager.getInstance().removeSettingUpdateListener(this);
    }

    public void onReveal(MouseEvent mouseEvent) {
        if (mouseEvent.isConsumed()) {
            return;
        }

        if (mouseEvent.getEventType() != MouseEvent.MOUSE_CLICKED || mouseEvent.getButton() != MouseButton.PRIMARY) {
            return;
        }

        if (revealed) {
            if (mouseEvent.getClickCount() == 2) {
                var pair = InspectController.createElement();

                pair.getKey().setUserData(tarot);

                Stage stage = new Stage();
                stage.setTitle("Tarot DB -- " + tarot.getName());
                stage.setScene(new Scene(pair.getKey()));
                stage.show();

                pair.getValue().attachEvents();
            }

            return;
        }

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.0)),
                new KeyFrame(Duration.seconds(0.8),
                        new KeyValue(imgImage.scaleXProperty(), 0.0, new Interpolator() {
                            @Override
                            protected double curve(double v) {
                                return 1.0 - Math.cos(v * Math.PI / 2);
                            }
                        }),
                        new KeyValue(imgImage.imageProperty(), tarot.getImageResource()),
                        new KeyValue(lblHead.opacityProperty(), 0.0)
                ),
                new KeyFrame(Duration.seconds(1.1),
                        new KeyValue(lblLabel.opacityProperty(), 0.0)
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(lblHead.opacityProperty(), 1.0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(1.6),
                        new KeyValue(lblLabel.opacityProperty(), 1.0, Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(1.6),
                        new KeyValue(imgImage.scaleXProperty(), 1.0, new Interpolator() {
                            @Override
                            protected double curve(double v) {
                                return Math.sin(v * Math.PI / 2);
                            }
                        })
                )
        );
        timeline.play();

        revealed = true;

        mouseEvent.consume();
    }

    public static Pair<Parent, TableCardController> createElement() {
        FXMLLoader loader = new FXMLLoader(Program.class.getResource("table-card-view.fxml"));
        Parent element;
        try {
            element = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Pair<>(element, loader.getController());
    }

}
