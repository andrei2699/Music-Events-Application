package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.SceneSwitchController;
import utils.StringValidator;

import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.TICKET_PRICE_TEXT;

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
    public TextField cvcTextField;

    @FXML
    public TextField cardNumberTextField;

    @FXML
    public DatePicker expirationDateDatePicker;

    @FXML
    public Label totalSeatsLabel;

    @FXML
    public Label ticketPriceLabel;

    private int maximumNumberOfSeats;
    private int ticketPrice;
    private ISceneResponseCall<Integer> numberOfSeatsResponseCall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedSeatsSlider.setMax(maximumNumberOfSeats);
        selectedSeatsSlider.setMin(1);
        selectedTextField.setText("1");
        totalSeatsLabel.setText("/ " + maximumNumberOfSeats);
        ticketPriceLabel.setText(TICKET_PRICE_TEXT + ": " + ticketPrice);

        selectedTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                selectedTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            try {
                selectedSeatsSlider.setValue(Double.parseDouble(selectedTextField.getText()));
            } catch (NumberFormatException ignored) {

            }
        });

        addTextNumberLimiter(cvcTextField, 3);
        addTextNumberLimiter(cardNumberTextField, 16);

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

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setPopupResponseCall(ISceneResponseCall<Integer> responseCall) {
        this.numberOfSeatsResponseCall = responseCall;
    }

    private void onReserveButtonClick(ActionEvent actionEvent) {
        if (cardNumberTextField.getText().length() == 16 && StringValidator.isStringNotEmpty(cardNumberTextField.getText()) &&
                cvcTextField.getText().length() == 3 && StringValidator.isStringNotEmpty(cvcTextField.getText()) &&
                expirationDateDatePicker.getValue() != null) {
            selectedSeatsSlider.requestFocus();
            if (numberOfSeatsResponseCall != null) {
                numberOfSeatsResponseCall.onResponseCall((int) selectedSeatsSlider.getValue());
            }
            closePopup(actionEvent);
        }
    }

    private void closePopup(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().closeReservationPopup();

    }

    private void addTextNumberLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }
}
