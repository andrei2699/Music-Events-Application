package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.EventModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EventListViewCellController implements Initializable {

    @FXML
    private AnchorPane cardPane;

    private FXMLLoader fxmlLoader;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label barNameLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Button showDetailsButton;

    private final EventModel model;

    public EventListViewCellController(EventModel model) {
        this.model = model;
    }

    @FXML
    public void onShowDetailsButtonClick(ActionEvent actionEvent) {
        System.out.println(artistNameLabel.getText());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventNameLabel.setText(model.getEventName());
        barNameLabel.setText(model.getBarName());
        artistNameLabel.setText(model.getArtistName());
        showDetailsButton.setOnAction(this::onShowDetailsButtonClick);
    }
}
