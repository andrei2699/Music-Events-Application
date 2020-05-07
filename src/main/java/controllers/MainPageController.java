package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventModel;
import models.UserModel;
import models.UserType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController extends ChangeableSceneController {

    @FXML
    private VBox evenCardsContainer;

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<EventModel> eventModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            eventModels.add(new EventModel("Event" + i, "Bar Name " + i, "Artist Name " + i));
        }

        for (EventModel model : eventModels) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/eventDetailsCard.fxml"));
            loader.setController(new EventListViewCellController(model));

            try {
                evenCardsContainer.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        eventsListView.setItems(eventModels);
//        eventsListView.setCellFactory(studentListView -> new EventListViewCellController());
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
