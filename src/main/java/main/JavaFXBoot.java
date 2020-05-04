package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BarModel;
import services.BarService;
import services.ServiceProvider;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceProvider.getFileSystemManager().createJSONFiles();

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, "/fxml/editBarProfilePage.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, "/fxml/register.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, "/fxml/login.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditBarProfileScene, "/fxml/editBarProfilePage.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditArtistProfileScene, "/fxml/editArtistProfilePage.fxml");

        sceneSwitchController.switchScene(SceneSwitchController.SceneType.LoginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
