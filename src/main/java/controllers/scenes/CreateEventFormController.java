package controllers.scenes;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventModel;
import models.UserModel;
import services.IEventService;
import services.ServiceProvider;
import services.IUserService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.INVALID_ARTIST_NAME_ERROR_MESSAGE;
import static main.ApplicationResourceStrings.REQUIRED_FIELD_ERROR_MESSAGE;

public class CreateEventFormController extends ChangeableSceneWithModelController {
    public static final String FORMULAR_EDITARE_EVENIMENT="Formular editare eveniment";
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
    public Label titleLabel;

    @FXML
    public ComboBox<Integer> startHourComboBox;

    @FXML
    public TextField seatNumberField;

    private IEventService eventService;

    private IUserService userService;

    private EventModel eventModel;

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

        seatNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                seatNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        barNameField.setText(LoggedUserData.getInstance().getUserModel().getName());
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
            if (eventModel == null) {
                int numberOfSeats = Integer.parseInt(seatNumberField.getText());
                eventService.createEvent(LoggedUserData.getInstance().getUserModel().getId(),
                        artistUserModel.getId(), eventNameField.getText(), datePicker.getValue().toString(),
                        startHourComboBox.getValue(), numberOfSeats, descriptionField.getText());

                SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.MainSceneContent);
            }
        }else {
            int numberOfSeats = Integer.parseInt(seatNumberField.getText());

            eventModel.setName(eventNameField.getText());
            eventModel.setDescription(descriptionField.getText());
            eventModel.setTotal_seats(numberOfSeats);
            eventModel.setStart_hour(startHourComboBox.getValue());
            eventModel.setDate(datePicker.getValue().toString());

            eventService.updateEvent(eventModel);
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.MainSceneContent);
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

    @Override
    public void onSetModelId(Integer modelId) {
        titleLabel.setText(FORMULAR_EDITARE_EVENIMENT);

        eventModel = eventService.getEventUsingEventId(modelId);
        if (eventModel == null)
            return;

        UserModel artistModel=userService.getUser(eventModel.getArtist_id());
        artistNameField.setText(artistModel.getName());
        eventNameField.setText(eventModel.getName());
        startHourComboBox.setValue(eventModel.getStart_hour());
        datePicker.setValue(LocalDate.parse(eventModel.getDate()));
        seatNumberField.setText(eventModel.getTotal_seats_string());
        descriptionField.setText(eventModel.getDescription());

        barNameField.setDisable(true);
        artistNameField.setDisable(true);
    }
}
