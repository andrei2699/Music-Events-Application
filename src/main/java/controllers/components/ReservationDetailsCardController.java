package controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
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
//            ReservationModel reservationModel = reservationCardModel.getArtistModel();
//
//            eventNameLabel.setText();
//            barNameLabel.setText();
//            artistNameLabel.setText();
//            numberOfSeatsLabel.setText();
//            dateLabel.setText();
//            startHourLabel.setText();

            setGraphic(reservationDetailsCardVBox);
        } else {
            setGraphic(null);
        }
    }
}
