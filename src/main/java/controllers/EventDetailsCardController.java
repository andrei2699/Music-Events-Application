package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventCardModel;
import models.EventModel;
import models.UserType;

import java.io.IOException;

public class EventDetailsCardController extends TableCell<MainPageController.EventModelContainer, EventCardModel> {

    private static final double MIN_DESCRIPTION_LABEL_WIDTH = 240;

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

    private EventModel eventModel;

    public EventDetailsCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/eventDetailsCard.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(EventCardModel eventCardModel, boolean empty) {
        super.updateItem(eventCardModel, empty);

        if (!empty && eventCardModel != null) {
            eventModel = eventCardModel.getEventModel();

            eventNameLabel.setText(eventModel.getName());
            barNameLabel.setText(eventCardModel.getBarName());
            artistNameLabel.setText(eventCardModel.getArtistName());
            dateLabel.setText(eventModel.getDate().toString());
            startHourLabel.setText(eventModel.getStart_hour() + "");
            updateNumberOfSeats();
            descriptionLabel.setText(eventModel.getDescription());

            reserveTicketButton.setOnAction(this::onReserveTicketButtonClick);

            Platform.runLater(() -> {
                double width = detailsTitledPaneContentVBox.getWidth();
                if (width == 0) {
                    width = MIN_DESCRIPTION_LABEL_WIDTH;
                }
                descriptionLabel.setPrefWidth(width);
            });

            if (!LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getType() != UserType.RegularUser) {
                hideControlsForNotRegisteredUsers();
            }

            setGraphic(eventCardVBox);
        } else {
            setGraphic(null);
        }
    }

    private void updateNumberOfSeats() {
        numberOfSeatsLabel.setText(eventModel.getReserved_seats() + " / " + eventModel.getTotal_seats());

        if (eventModel.getAvailableSeats() <= 0) {
            reserveTicketButton.setDisable(true);
        }
    }

    private void hideControlsForNotRegisteredUsers() {
        eventCardVBox.getChildren().remove(actionButtonsHBox);
        eventCardVBox.getChildren().remove(actionButtonsSeparator);
        detailsTitledPaneContentVBox.getChildren().remove(numberOfSeatsHBox);
    }

    private void onReserveTicketButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().showReservationPopup(eventModel.getAvailableSeats(), numberOfSeats -> {
            eventModel.addReservedSeats(numberOfSeats);
            updateNumberOfSeats();
        });
    }
}
