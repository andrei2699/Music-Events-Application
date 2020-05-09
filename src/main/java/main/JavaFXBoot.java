package main;

import javafx.application.Application;
import javafx.stage.Stage;
import services.ServiceProvider;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceProvider.getFileSystemManager().createJSONFiles();

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, "/fxml/scenes/main.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, "/fxml/scenes/register.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, "/fxml/scenes/login.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditBarProfileScene, "/fxml/scenes/editBarProfilePage.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditArtistProfileScene, "/fxml/scenes/editArtistProfilePage.fxml");

        sceneSwitchController.switchScene(SceneSwitchController.SceneType.LoginScene);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
