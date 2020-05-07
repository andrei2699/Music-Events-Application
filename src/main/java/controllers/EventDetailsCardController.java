package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.EventModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EventDetailsCardController implements Initializable {

    @FXML
    private VBox eventCardVBox;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label barNameLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label startHourLabel;

    @FXML
    private Label numberOfSeatsLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TitledPane detailsTitlePane;

    @FXML
    private Button reserveTicketButton;

    @FXML
    private Separator actionButtonsSeparator;

    @FXML
    private VBox detailsTitledPaneContentVBox;

    @FXML
    private HBox numberOfSeatsHBox;

    @FXML
    private HBox actionButtonsHBox;

    private FXMLLoader fxmlLoader;
    private final EventModel model;

    public EventDetailsCardController(EventModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventNameLabel.setText(model.getEventName());
        barNameLabel.setText(model.getBarName());
        artistNameLabel.setText(model.getArtistName());
        dateLabel.setText(model.getDate());
        startHourLabel.setText(model.getStartHour());
        numberOfSeatsLabel.setText(model.getNumberOfSeats() + "");
        descriptionLabel.setText(model.getDescription());

        reserveTicketButton.setOnAction(this::onReserveTicketButtonClick);

        Platform.runLater(() -> descriptionLabel.setPrefWidth(detailsTitledPaneContentVBox.getWidth()));

//        Platform.runLater(() -> {
//
//            Node plus = new Circle(10);
//            Node minus = new Circle(2);
//
//            detailsTitlePane.graphicProperty().bind(
//                    Bindings.when(detailsTitlePane.expandedProperty())
//                            .then(minus)
//                            .otherwise(plus));
//            detailsTitlePane.lookup(".arrow").setVisible(false);
//        });
    }

    public void hideControlsForNotRegisteredUsers() {
        eventCardVBox.getChildren().remove(actionButtonsHBox);
        eventCardVBox.getChildren().remove(actionButtonsSeparator);
        detailsTitledPaneContentVBox.getChildren().remove(numberOfSeatsHBox);
    }

    private void onReserveTicketButtonClick(ActionEvent actionEvent) {

    }
}
