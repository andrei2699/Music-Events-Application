package controllers;

import javafx.application.Platform;
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
            EventModel model = new EventModel("Event" + i, "Bar Name " + i, "Artist Name " + i, "DateTime " + i, (i + 12) + "", i * 3);
            if (i % 2 == 0) {
                model.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            }
            eventModels.add(model);
        }

        for (EventModel model : eventModels) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/eventDetailsCard.fxml"));
            EventDetailsCardController controller = new EventDetailsCardController(model);
            loader.setController(controller);

            try {
                evenCardsContainer.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getType() != UserType.RegularUser) {
                controller.hideControlsForNotRegisteredUsers();
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
