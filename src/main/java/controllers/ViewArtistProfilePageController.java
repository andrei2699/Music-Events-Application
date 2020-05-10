package controllers;

import javafx.event.ActionEvent;
import main.SceneSwitchController;

import static main.SceneSwitchController.SceneType.ViewArtistProfileScene;

public class ViewArtistProfilePageController extends ViewProfileAbstractController {
    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {

    }

    @Override
    protected void updateUIOnSceneChanged() {

    }

    @Override
    public void onSetUserModelId(Integer userModelId) {

    }

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return ViewArtistProfileScene;
    }
}
