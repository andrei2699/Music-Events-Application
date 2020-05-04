package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.DisponibilitiesSchedule;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;
import services.UserService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static models.DaysOfWeek.NumberOfDays;

public class EditBarProfilePageController extends ChangeableSceneController {
    @FXML
    public ImageView profilePhoto;

    @FXML
    public TextField userNameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    @FXML
    public TextField addressField;

    @FXML
    public GridPane scheduleGridPane;

    private UserService userService;
    private BarService barService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        userService = ServiceProvider.getUserService();
        barService = ServiceProvider.getBarService();
    }

    @Override
    public void onSceneChanged() {
        // make a save to the database to create the bar record
        onSaveChangesButtonClick(null);

        fillFieldsWithValuesFromLoggedUserData();
        fillScheduleGridPane(scheduleGridPane, null);
    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
        // todo change to (when finish editing this page)
//        return SceneSwitchController.SceneType.EditBarProfileScene;
    }

    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {
        userNameField.setText("JKANSJFNJDAGN");
    }

    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        BarModel bar = barService.getBar(userModel.getId());
        if (bar == null) {
            bar = new BarModel(userModel.getId(), "", "");
        }
        barService.createBar(bar);

        // todo update user model with user service
        // todo change LoggedUserData with the updated data
    }

    private void fillFieldsWithValuesFromLoggedUserData() {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        while (userNameField.getText().equals(userModel.getName())) {
            userNameField.setText(userModel.getName());
        }
        emailField.setText(userModel.getEmail());
        userTypeField.setText(userModel.getType().toString());
        BarModel barModel = barService.getBar(userModel.getId());
        if (barModel == null) {
            return;
        }

        addressField.setText(barModel.getAddress());
        String pathToImageFile = barModel.getPath_to_image();

        if (pathToImageFile.isEmpty()) {
            pathToImageFile = getClass().getResource("/Images/defaultUserPhoto.jpg").getPath();
        }

        File file = new File(pathToImageFile);
        profilePhoto.setImage(new Image(file.toURI().toString()));
    }

    private void fillScheduleGridPane(GridPane gridPane, DisponibilitiesSchedule schedule) {
        HBox[][] hBoxes = new HBox[3][]; // 3 = day of week, start_hour, end_hour

        for (int row = 0; row < hBoxes.length; row++) {
            hBoxes[row] = new HBox[NumberOfDays + 1];

            for (int column = 0; column < hBoxes[row].length; column++) {
                hBoxes[row][column] = new HBox();
                hBoxes[row][column].setAlignment(Pos.CENTER);
                gridPane.add(hBoxes[row][column], row, column);
            }
        }

        Label dayOfWeekLabel = new Label("Zi");
        hBoxes[0][0].getChildren().add(dayOfWeekLabel);

        Label startHourLabel = new Label("Inceput");
        hBoxes[0][1].getChildren().add(startHourLabel);

        Label endHourLabel = new Label("Sfarsit");
        hBoxes[0][2].getChildren().add(endHourLabel);
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {

    }
}
