package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class BarProfilePageController extends ChangeableSceneController {

    @FXML
    public ImageView profilePhoto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        File file=new File("C:/Users/Tania/Desktop/patrat.png");
//        profilePhoto.setImage(new Image(file.toURI().toString()));
    }

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.BarProfileScene;
    }
}
