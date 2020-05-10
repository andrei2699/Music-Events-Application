package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeReservationPopupWindowController implements Initializable {

    @FXML
    public Slider selectedSeatsSlider;

    @FXML
    public Label selectedSeatsLabel;

    @FXML
    public Button reserveButton;

    private int maximumNumberOfSeats;
    private ISceneResponseCall<Integer> numberOfSeatsResponseCall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedSeatsSlider.setMax(maximumNumberOfSeats);
        selectedSeatsLabel.setText("0");

        selectedSeatsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            selectedSeatsSlider.setValue(newValue.intValue());
            int intValue = (int) selectedSeatsSlider.getValue();

            selectedSeatsLabel.setText(String.valueOf(intValue));
        });

        reserveButton.setOnAction(this::onReserveButtonClick);
    }

    public void setMaximumNumberOfSeats(int maximumNumberOfSeats) {
        this.maximumNumberOfSeats = maximumNumberOfSeats;
    }

    public void setPopupResponseCall(ISceneResponseCall<Integer> responseCall) {
        this.numberOfSeatsResponseCall = responseCall;
    }

    private void onReserveButtonClick(ActionEvent actionEvent) {
        if (numberOfSeatsResponseCall != null) {
            numberOfSeatsResponseCall.onResponseCall((int) selectedSeatsSlider.getValue());
        }

        SceneSwitchController.getInstance().closeReservationPopup();
    }
}
