package controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import models.EventModel;
import models.ReservationModel;
import models.cards.ReservationCardModel;
import models.cards.TableCardModel;

import java.io.IOException;

import static main.ApplicationResourceStrings.RESERVATION_DETAILS_CARD_FXML_PATH;

public class ReservationDetailsCardController extends TableCell<TableCardModel, TableCardModel> {
    @FXML
    private VBox reservationDetailsCardVBox;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label barNameLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Label numberOfSeatsLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label startHourLabel;

    public ReservationDetailsCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RESERVATION_DETAILS_CARD_FXML_PATH));
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
            ReservationCardModel reservationCardModel = (ReservationCardModel) tableCardModel;
            ReservationModel reservationModel = reservationCardModel.getReservationModel();
            EventModel eventModel = reservationCardModel.getEventModel();

            eventNameLabel.setText(eventModel.getName());
            barNameLabel.setText(reservationCardModel.getBarName());
            artistNameLabel.setText(reservationCardModel.getArtistName());
            numberOfSeatsLabel.setText(reservationModel.getReserved_seats() + "");
            dateLabel.setText(eventModel.getDate());
            startHourLabel.setText(eventModel.getStart_hour() + "");

            setGraphic(reservationDetailsCardVBox);
        } else {
            setGraphic(null);
        }
    }
}
