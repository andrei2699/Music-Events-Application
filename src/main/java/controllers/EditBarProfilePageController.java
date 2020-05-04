package controllers;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.*;
import services.BarService;
import services.ServiceProvider;
import services.UserService;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private BarService barService;

    private BarModel barModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barService = ServiceProvider.getBarService();

        if (LoggedUserData.getInstance().isUserLogged()) {
            UserModel userModel = LoggedUserData.getInstance().getUserModel();
            barModel = barService.getBar(userModel.getId());
            if (barModel == null) {
                barModel = new BarModel(userModel.getId(), "", "");
            }
            barService.createBar(barModel);
        }
    }

    @Override
    public void onSceneChanged() {
        // make a save to the database to create the bar record
        onSaveChangesButtonClick(null);

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                fillFieldsWithValuesFromLoggedUserData();
                List<Interval> intervals;
                if (barModel == null) {
                    intervals = null;
                } else {
                    intervals = barModel.getIntervals();
                }
                fillScheduleGridPane(scheduleGridPane, intervals);
            }
        });
        new Thread(sleeper).start();
    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
        // todo change to (when finish editing this page)
//        return SceneSwitchController.SceneType.EditBarProfileScene;
    }

    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege poza profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(SceneSwitchController.getInstance().getStage());
        if (selectedFile != null) {
            barModel.setPath_to_image(selectedFile.getPath());
            setProfileImage(selectedFile.getPath());

            barService.updateBar(barModel);
        }
    }

    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (barModel == null) {
            return;
        }


//        barService.updateBar(barModel);
        // todo update user model with user service
        // todo change LoggedUserData with the updated data
    }

    private void fillFieldsWithValuesFromLoggedUserData() {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        userNameField.setText(userModel.getName());
        emailField.setText(userModel.getEmail());
        userTypeField.setText(userModel.getType().toString());
        BarModel barModel = barService.getBar(userModel.getId());
        if (barModel == null) {
            return;
        }

        addressField.setText(barModel.getAddress());
        setProfileImage(barModel.getPath_to_image());
    }

    private void setProfileImage(String pathToImageFile) {
        if (pathToImageFile.isEmpty()) {
            try {
                pathToImageFile = Paths.get(getClass().getResource("/Images/defaultUserPhoto.jpg").toURI()).toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File file = new File(pathToImageFile);
        profilePhoto.setImage(new Image(file.toURI().toString()));
    }

    private List<Interval> getIntervalsFromGridPane(GridPane gridPane) {
        return new ArrayList<>();
    }

    private void fillScheduleGridPane(GridPane gridPane, List<Interval> intervals) {
        HBox[][] hBoxes = new HBox[NumberOfDays + 1][3]; // 3 = day of week, start_hour, end_hour

        for (int row = 0; row < hBoxes.length; row++) {
            for (int column = 0; column < hBoxes[row].length; column++) {
                hBoxes[row][column] = new HBox();
                hBoxes[row][column].setAlignment(Pos.CENTER);
                gridPane.add(hBoxes[row][column], row, column);
            }
        }

        hBoxes[0][0].getChildren().add(new Label("Zi"));

        int dayOfWeekIndex = 1;
        for (DaysOfWeek daysOfWeek : DaysOfWeek.values()) {
            hBoxes[dayOfWeekIndex][0].getChildren().add(new Label(daysOfWeek.toString()));
            dayOfWeekIndex++;
        }

        hBoxes[0][1].getChildren().add(new Label("Inceput"));

        hBoxes[0][2].getChildren().add(new Label("Sfarsit"));

        Integer[] hours = new Integer[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }

        for (int row = 1; row < hBoxes.length; row++) {
            for (int column = 1; column < hBoxes[row].length; column++) {
                ComboBox<Integer> comboBox = new ComboBox<>();
                comboBox.setItems(FXCollections.observableArrayList(hours));
                hBoxes[row][column].getChildren().add(comboBox);
            }
        }
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {

    }
}
