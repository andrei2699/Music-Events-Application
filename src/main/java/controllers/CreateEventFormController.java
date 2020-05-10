package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventModel;
import models.UserModel;
import services.EventService;
import services.ServiceProvider;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

import static main.SceneSwitchController.SceneType.CreateEventFormScene;
import static main.SceneSwitchController.SceneType.MainScene;

public class CreateEventFormController extends ChangeableSceneController {
    private static final String REQUIRED_FIELD_ERROR_MESSAGE = "* Camp Obligatoriu";
    private static final String INVALID_ARTIST_NAME_ERROR_MESSAGE = "* Artist/formatie inexistent(a)";

    @FXML
    public TextField eventNameField;

    @FXML
    public TextField barNameField;

    @FXML
    public TextField artistNameField;

    @FXML
    public TextArea descriptionField;

    @FXML
    public DatePicker datePicker;

    @FXML
    public Label eventNameErrorLabel;

    @FXML
    public Label artistNameErrorLabel;

    @FXML
    public Label startHourErrorLabel;

    @FXML
    public Label seatNumberErrorLabel;

    @FXML
    public Label dateErrorLabel;

    @FXML
    public ComboBox<Integer> startHourComboBox;

    @FXML
    public TextField seatNumberField;

    private EventService eventService;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService = ServiceProvider.getEventService();
        userService = ServiceProvider.getUserService();

        setAllLabelsInvisible();

        Integer[] hours = new Integer[24];

        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }

        startHourComboBox.setItems(FXCollections.observableArrayList(hours));

        seatNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    seatNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        barNameField.setText(LoggedUserData.getInstance().getUserModel().getName());
    }


    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return CreateEventFormScene;
    }

    @FXML
    public void onSaveDetailsButtonClick(ActionEvent actionEvent) {

        setAllLabelsInvisible();
        boolean canSaveDetails = true;

        UserModel artistUserModel = userService.getArtist(artistNameField.getText());

        if (eventNameField.getText().isEmpty() || eventNameField.getText().isBlank()) {
            showErrorLabel(eventNameErrorLabel);
            canSaveDetails = false;
        }
        if (datePicker.getValue() == null) {
            showErrorLabel(dateErrorLabel);
            canSaveDetails = false;
        }
        if (startHourComboBox.getValue() == null) {
            showErrorLabel(startHourErrorLabel);
            canSaveDetails = false;
        }
        if (seatNumberField.getText().isEmpty() || seatNumberField.getText().isBlank()) {
            showErrorLabel(seatNumberErrorLabel);
            canSaveDetails = false;
        }
        if (artistUserModel == null) {
            showErrorLabel(artistNameErrorLabel, INVALID_ARTIST_NAME_ERROR_MESSAGE);
            canSaveDetails = false;
        }
        if (artistNameField.getText().isEmpty() || artistNameField.getText().isBlank()) {
            showErrorLabel(artistNameErrorLabel, REQUIRED_FIELD_ERROR_MESSAGE);
            canSaveDetails = false;
        }
        if (canSaveDetails) {
            int numberOfSeats = Integer.parseInt(seatNumberField.getText());
            eventService.createEvent(LoggedUserData.getInstance().getUserModel().getId(),
                    artistUserModel.getId(), eventNameField.getText(), datePicker.getValue(),
                    startHourComboBox.getValue(), numberOfSeats, descriptionField.getText());

            SceneSwitchController.getInstance().switchScene(MainScene);
        }
    }

    private void setAllLabelsInvisible() {
        eventNameErrorLabel.setVisible(false);
        dateErrorLabel.setVisible(false);
        artistNameErrorLabel.setVisible(false);
        seatNumberErrorLabel.setVisible(false);
        startHourErrorLabel.setVisible(false);
    }

    private void showErrorLabel(Label label) {
        label.setVisible(true);
    }

    private void showErrorLabel(Label label, String text) {
        label.setVisible(true);
        label.setText(text);
    }

}