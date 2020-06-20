package controllers.components;

import export.ExportAsPDF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.SceneSwitchController;
import models.EventModel;
import models.ReservationModel;
import models.cards.ReservationCardModel;
import models.cards.TableCardModel;

import java.io.File;
import java.io.IOException;

import static main.ApplicationResourceStrings.*;

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

    @FXML
    private Button exportPDFButton;

    private ReservationCardModel reservationCardModel;

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
            reservationCardModel = (ReservationCardModel) tableCardModel;
            ReservationModel reservationModel = reservationCardModel.getReservationModel();
            EventModel eventModel = reservationCardModel.getEventModel();

            exportPDFButton.setOnAction(this::onExportPDFButtonClick);

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

    public void onExportPDFButtonClick(ActionEvent actionEvent) {
        artistNameLabel.requestFocus();
        File selectedFile = openFileChooser();
        if (selectedFile != null) {
            ExportAsPDF exportPDF = new ExportAsPDF();
            exportPDF.export(selectedFile, reservationCardModel.convertCardModelToExportRowList(), RESERVATION_TEXT);
        }
    }

    protected final File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(CHOOSE_PDF_DESTINATION_TEXT);
        fileChooser.setInitialFileName(DEFAULT_PDF_FILE_NAME);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        return fileChooser.showSaveDialog(SceneSwitchController.getInstance().getPrimaryStage());
    }
}
