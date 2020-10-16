package controllers.components;

import controllers.scenes.ISceneResponseCall;
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
import models.ReservationModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.IEventService;
import services.IReservationService;
import services.ServiceProvider;
import services.implementation.EventNotDeletedException;
import services.implementation.ReservationNotCreatedException;
import services.implementation.ReservationNotDeletedException;

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
    public Button deleteEventButton;

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
    private IEventService eventService;

    private ISceneResponseCall<Integer> idResponseCall;

    // for testing
    protected EventDetailsCardController(IReservationService reservationService, IEventService eventService) {
        this.reservationService = reservationService;
        this.eventService = eventService;
    }

    // for FXML reflection
    public EventDetailsCardController() {
        this(ServiceProvider.getReservationService(), ServiceProvider.getEventService());
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
            deleteEventButton.setOnAction(this::onDeleteEventButtonClick);

            Platform.runLater(() -> {
                double width = detailsTitledPaneContentVBox.getWidth();
                if (width == 0) {
                    width = MIN_DESCRIPTION_LABEL_WIDTH;
                }
                descriptionLabel.setPrefWidth(width);
            });

            if (LoggedUserData.getInstance().isRegularUser()) {
                actionButtonsHBox.getChildren().remove(editEventButton);
                actionButtonsHBox.getChildren().remove(deleteEventButton);
            } else if (LoggedUserData.getInstance().isBarManager()) {

                if (eventModel.getBar_manager_id() != LoggedUserData.getInstance().getUserModel().getId()) {
                    hideControlsForNotRegUserOrBar();
                } else {
                    if (reservationService.getReservationUsingEventId(eventModel.getId()).size() > 0) {
                        editEventButton.setDisable(true);
                        deleteEventButton.setDisable(true);
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
            try {
                reservationService.makeReservation(LoggedUserData.getInstance().getUserModel().getId(), eventModel.getId(), numberOfSeats);
            } catch (ReservationNotCreatedException e) {
                e.printStackTrace();
            }
        });
    }

    private void onEditEventButtonClick(ActionEvent actionEvent) {
        barNameLabel.requestFocus();
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.CreateEventFormContentScene, eventModel.getId());
    }

    public void setResponseCall(ISceneResponseCall<Integer> responseCall) {
        idResponseCall = responseCall;
    }

    private void onDeleteEventButtonClick(ActionEvent actionEvent)  {
        barNameLabel.requestFocus();
        try
        {
            int id=eventModel.getId();
            eventService.deleteEvent(id);
            if(idResponseCall != null) {
                idResponseCall.onResponseCall(eventModel.getId());
            }
        }

        catch (EventNotDeletedException e) {
            e.printStackTrace();
        }
    }
}
