package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventModel;
import models.UserModel;
import models.UserType;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ChangeableSceneController {

    @FXML
    private ListView<EventModel> eventsListView;

    private final ObservableList<EventModel> eventModelObservableList;

    public MainPageController() {
        eventModelObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < 10; i++) {
            eventModelObservableList.add(new EventModel("Event" + i, "Bar Name " + i, "Artist Name " + i));
        }
    }

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventsListView.setItems(eventModelObservableList);
        eventsListView.setCellFactory(studentListView -> new EventListViewCellController());
    }

    public void goEditProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditArtistProfileScene);
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
        }
    }
}
