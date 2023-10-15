package me.bloodwiing.tarotdb.controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InspectRowController {

    @FXML
    private Label labelName;

    @FXML
    private VBox vboxList;

    public void setName(String name) {
        labelName.setText(name);
    }

    public ObservableList<Node> getListItems() {
        return vboxList.getChildren();
    }

}
