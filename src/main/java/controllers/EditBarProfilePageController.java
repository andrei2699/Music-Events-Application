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
import javafx.stage.FileChooser;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.DaysOfWeek;
import models.Interval;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static models.DaysOfWeek.Duminica;
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
    private HBox[][] gridHBoxes;

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

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<>() {
            @Override
            public void handle(WorkerStateEvent event) {
                fillFieldsWithValuesFromLoggedUserData();
                List<Interval> intervals;
                if (barModel == null) {
                    intervals = null;
                } else {
                    intervals = barModel.getIntervals();
                }

                gridHBoxes = fillScheduleGridPane(scheduleGridPane, intervals);
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

        List<Interval> intervalsFromGridPane = getIntervalsFromGrid(gridHBoxes);

        barModel.setAddress(addressField.getText());
        barModel.setName(userNameField.getText());
        barModel.setIntervals(intervalsFromGridPane);

        barService.updateBar(barModel);
    }

    private void fillFieldsWithValuesFromLoggedUserData() {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        userNameField.setText(barModel.getName());
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

    public static List<Interval> getIntervalsFromGrid(HBox[][] hBoxes) {

        if (hBoxes == null) {
            return null;
        }

        List<Interval> intervals = new ArrayList<>();

        for (DaysOfWeek day : DaysOfWeek.values()) {
            intervals.add(new Interval(day, 0, 0));
        }

        for (int column = 1; column < hBoxes.length; column++) {
            ComboBox startHourComboBox = (ComboBox) hBoxes[column][1].getChildren().get(0);
            ComboBox endHourComboBox = (ComboBox) hBoxes[column][2].getChildren().get(0);

            if (startHourComboBox.getValue() == null) {
                intervals.get(column - 1).setStart_hour(null);
            } else {
                intervals.get(column - 1).setStart_hour(Integer.parseInt(startHourComboBox.getValue().toString()));
            }


            if (endHourComboBox.getValue() == null) {
                intervals.get(column - 1).setEnd_hour(null);
            } else {
                intervals.get(column - 1).setEnd_hour(Integer.parseInt(endHourComboBox.getValue().toString()));
            }
        }


        return intervals;
    }

    public static HBox[][] fillScheduleGridPane(GridPane gridPane, List<Interval> intervals) {
        HBox[][] hBoxes = new HBox[NumberOfDays + 1][3]; // 3 = day of week, start_hour, end_hour


        Integer[] hours = new Integer[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }

        for (int column = 0; column < hBoxes.length; column++) {
            for (int row = 0; row < hBoxes[column].length; row++) {
                hBoxes[column][row] = new HBox();
                hBoxes[column][row].setAlignment(Pos.CENTER);
                gridPane.add(hBoxes[column][row], column, row);
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


        for (int column = 1; column < hBoxes.length; column++) {
            for (int row = 1; row < hBoxes[column].length; row++) {
                ComboBox<Integer> comboBox = new ComboBox<>();

                if (row == 1) {
                    comboBox.setValue(intervals.get(column - 1).getStart_hour());
                } else if (row == 2) {
                    comboBox.setValue(intervals.get(column - 1).getEnd_hour());
                }

                comboBox.setItems(FXCollections.observableArrayList(hours));
                hBoxes[column][row].getChildren().add(comboBox);
            }
        }
        return hBoxes;
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {
        onSaveChangesButtonClick(actionEvent);
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }
}
