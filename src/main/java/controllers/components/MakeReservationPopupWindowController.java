package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeReservationPopupWindowController implements Initializable {

    @FXML
    public Slider selectedSeatsSlider;

    @FXML
    public Button reserveButton;

    @FXML
    public Button cancelButton;

    @FXML
    public TextField selectedTextField;

    @FXML
    public Label totalSeatsLabel;

    private int maximumNumberOfSeats;
    private ISceneResponseCall<Integer> numberOfSeatsResponseCall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedSeatsSlider.setMax(maximumNumberOfSeats);
        selectedSeatsSlider.setMin(1);
        selectedTextField.setText("1");
        totalSeatsLabel.setText("/ " + maximumNumberOfSeats);

        selectedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                selectedTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            try {
                selectedSeatsSlider.setValue(Double.parseDouble(selectedTextField.getText()));
            } catch (NumberFormatException ignored) {

            }
        });

        selectedSeatsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int intValue = (int) selectedSeatsSlider.getValue();

            selectedTextField.setText(String.valueOf(intValue));
        });

        reserveButton.setOnAction(this::onReserveButtonClick);

        cancelButton.setOnAction(this::closePopup);
    }

    public void setMaximumNumberOfSeats(int maximumNumberOfSeats) {
        this.maximumNumberOfSeats = maximumNumberOfSeats;
    }

    public void setPopupResponseCall(ISceneResponseCall<Integer> responseCall) {
        this.numberOfSeatsResponseCall = responseCall;
    }

    private void onReserveButtonClick(ActionEvent actionEvent) {
        selectedSeatsSlider.requestFocus();
        if (numberOfSeatsResponseCall != null) {
            numberOfSeatsResponseCall.onResponseCall((int) selectedSeatsSlider.getValue());
        }
        closePopup(actionEvent);
    }

    private void closePopup(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().closeReservationPopup();

    }
}
