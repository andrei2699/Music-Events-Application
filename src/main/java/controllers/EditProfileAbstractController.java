package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import main.SceneSwitchController;
import models.DaysOfWeek;
import models.Interval;
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

public abstract class EditProfileAbstractController extends ChangeableSceneController {

    private static final String IMAGES_DEFAULT_USER_PHOTO = "/Images/defaultUserPhoto.png";

    @FXML
    public ImageView profilePhoto;

    @FXML
    public TextField nameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    @FXML
    public GridPane scheduleGridPane;

    protected HBox[][] gridHBoxes;
    protected UserService userService;

    protected abstract void onSaveChangesButtonClick(ActionEvent actionEvent);

    protected abstract void updateUIOnSceneChanged();

    protected abstract void fillFieldsWithValuesFromLoggedUserData();

    protected abstract void onChoosePhotoButtonClick(ActionEvent actionEvent);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceProvider.getUserService();
    }

    @Override
    public void onSceneChanged() {
        // make a save to the database to create the bar record
//        onSaveChangesButtonClick(null);
//
//        Task<Void> sleeper = new Task<>() {
//            @Override
//            protected Void call() {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ignored) {
//                }
//                return null;
//            }
//        };
//        sleeper.setOnSucceeded(event -> updateUIOnSceneChanged());
//        new Thread(sleeper).start();
        updateUIOnSceneChanged();
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {
        onSaveChangesButtonClick(actionEvent);
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }

    protected File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege poza profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(SceneSwitchController.getInstance().getStage());
    }

    protected List<Interval> getIntervalsFromGrid(HBox[][] hBoxes) {

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

    protected HBox[][] fillScheduleGridPane(GridPane gridPane, List<Interval> intervals) {
        HBox[][] hBoxes = new HBox[NumberOfDays + 1][3]; // 3 = day of week, start_hour, end_hour


        Integer[] hours = new Integer[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }

        for (int column = 0; column < hBoxes.length; column++) {
            for (int row = 0; row < hBoxes[column].length; row++) {
                hBoxes[column][row] = new HBox();
                hBoxes[column][row].setAlignment(Pos.CENTER);
                hBoxes[column][row].setPadding(new Insets(0, 2, 0, 2));
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

                if (intervals != null && intervals.size() > 0) {
                    Interval interval = intervals.get(column - 1);
                    if (row == 1 && interval != null) {
                        comboBox.setValue(interval.getStart_hour());
                    } else if (row == 2 && interval != null) {
                        comboBox.setValue(interval.getEnd_hour());
                    }
                }

                comboBox.setItems(FXCollections.observableArrayList(hours));
                hBoxes[column][row].getChildren().add(comboBox);
            }
        }
        return hBoxes;
    }

    protected Image getProfileImage(String pathToImageFile) {
        if (pathToImageFile.isEmpty()) {
            try {
                pathToImageFile = Paths.get(getClass().getResource(IMAGES_DEFAULT_USER_PHOTO).toURI()).toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File file = new File(pathToImageFile);
        return new Image(file.toURI().toString(), true);
    }

}
