package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import models.EventModel;

import java.io.IOException;

public class EventListViewCellController extends ListCell<EventModel> {

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

    @Override
    protected void updateItem(EventModel model, boolean empty) {
        super.updateItem(model, empty);

        if (empty || model == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/eventListCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            eventNameLabel.setText(model.getEventName());
            barNameLabel.setText(model.getBarName());
            artistNameLabel.setText(model.getArtistName());

            showDetailsButton.setOnAction(this::onShowDetailsButtonClick);

            setText(null);
            setGraphic(cardPane);
        }
    }

    public void onShowDetailsButtonClick(ActionEvent actionEvent) {
        System.out.println(artistNameLabel.getText());

    }
}
