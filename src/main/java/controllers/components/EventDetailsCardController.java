package controllers.components;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.EventModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.ReservationService;
import services.ServiceProvider;

import java.io.IOException;

import static main.ApplicationResourceStrings.EVENT_DETAILS_CARD_FXML_PATH;

public class EventDetailsCardController extends TableCell<TableCardModel, TableCardModel> {
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
    private Button editEventButton;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(EVENT_DETAILS_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(TableCardModel tableCardModel, boolean empty) {
        super.updateItem(tableCardModel, empty);

        if (!empty && tableCardModel != null) {

            EventCardModel eventCardModel = (EventCardModel) tableCardModel;

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

            if (LoggedUserData.getInstance().isRegularUser()) {
                actionButtonsHBox.getChildren().remove(editEventButton);
            } else if (LoggedUserData.getInstance().isBarManager()) {

                if (eventModel.getBar_manager_id() != LoggedUserData.getInstance().getUserModel().getId()) {
                    hideControlsForNotRegUserOrBar();
                } else {
                    actionButtonsHBox.getChildren().remove(reserveTicketButton);
                }
            } else {
                hideControlsForNotRegUserOrBar();
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

    private void hideControlsForNotRegUserOrBar() {
        eventCardVBox.getChildren().remove(actionButtonsHBox);
        eventCardVBox.getChildren().remove(actionButtonsSeparator);
        detailsTitledPaneContentVBox.getChildren().remove(numberOfSeatsHBox);
    }

    private void onReserveTicketButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().showReservationPopup(eventModel.getAvailableSeats(), numberOfSeats -> {
            eventModel.addReservedSeats(numberOfSeats);
            updateNumberOfSeats();
            ReservationService reservationService = ServiceProvider.getReservationService();
            reservationService.makeReservation(LoggedUserData.getInstance().getUserModel().getId(), eventModel.getId(), numberOfSeats);
        });
    }
}
