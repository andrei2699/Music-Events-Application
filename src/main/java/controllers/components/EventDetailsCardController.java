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
import models.EventModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.IEventService;
import services.IReservationService;
import services.ServiceProvider;

import java.io.IOException;

import static main.ApplicationResourceStrings.EVENT_DETAILS_CARD_FXML_PATH;

public class EventDetailsCardController extends TableCell<TableCardModel, TableCardModel> {
    private static final double MIN_DESCRIPTION_LABEL_WIDTH = 240;

    @FXML
    public VBox eventCardVBox;

    @FXML
    public Label eventNameLabel;

    @FXML
    public Label barNameLabel;

    @FXML
    public Label artistNameLabel;

    @FXML
    public Label dateLabel;

    @FXML
    public Label startHourLabel;

    @FXML
    public Label numberOfSeatsLabel;

    @FXML
    public Label descriptionLabel;

    @FXML
    public Button reserveTicketButton;

    @FXML
    public Button editEventButton;

    @FXML
    public Separator actionButtonsSeparator;

    @FXML
    public VBox detailsTitledPaneContentVBox;

    @FXML
    public HBox numberOfSeatsHBox;

    @FXML
    public HBox actionButtonsHBox;

    @FXML
    public Label notEditableMessageLabel;

    private EventModel eventModel;
    private IReservationService reservationService;

    // for testing
    protected EventDetailsCardController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // for FXML reflection
    public EventDetailsCardController() {
        this(ServiceProvider.getReservationService());
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

        if (!empty && tableCardModel instanceof EventCardModel) {

            EventCardModel eventCardModel = (EventCardModel) tableCardModel;

            eventModel = eventCardModel.getEventModel();

            eventNameLabel.setText(eventModel.getName());
            barNameLabel.setText(eventCardModel.getBarName());
            artistNameLabel.setText(eventCardModel.getArtistName());
            dateLabel.setText(eventModel.getDate());
            startHourLabel.setText(eventModel.getStart_hour() + "");
            updateNumberOfSeats();
            descriptionLabel.setText(eventModel.getDescription());

            reserveTicketButton.setOnAction(this::onReserveTicketButtonClick);
            editEventButton.setOnAction(this::onEditEventButtonClick);

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
                    if (reservationService.getReservationUsingEventId(eventModel.getId()).size() > 0) {
                        editEventButton.setDisable(true);
                        notEditableMessageLabel.setVisible(true);
                    }
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
        barNameLabel.requestFocus();
        SceneSwitchController.getInstance().showReservationPopup(eventModel.getAvailableSeats(), numberOfSeats -> {
            eventModel.addReservedSeats(numberOfSeats);
            updateNumberOfSeats();

            IEventService eventService = ServiceProvider.getEventService();
            eventService.updateEvent(eventModel);

            IReservationService reservationService = ServiceProvider.getReservationService();
            reservationService.makeReservation(LoggedUserData.getInstance().getUserModel().getId(), eventModel.getId(), numberOfSeats);
        });
    }

    private void onEditEventButtonClick(ActionEvent actionEvent) {
        barNameLabel.requestFocus();
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.CreateEventFormContentScene, eventModel.getId());
    }
}
