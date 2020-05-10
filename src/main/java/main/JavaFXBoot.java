package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import services.ServiceProvider;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceProvider.getFileSystemManager().createJSONFiles();

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setPrimaryStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, "/fxml/scenes/main.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, "/fxml/scenes/register.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, "/fxml/scenes/login.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.ViewBarProfileScene, "/fxml/scenes/viewBarProfilePage.fxml");

        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditBarProfileScene, "/fxml/scenes/editBarProfilePage.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditArtistProfileScene, "/fxml/scenes/editArtistProfilePage.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.CreateEventFormScene, "/fxml/scenes/createEventForm.fxml");
        sceneSwitchController.addScene(SceneSwitchController.SceneType.ViewBarProfileScene, "/fxml/scenes/barProfilePage.fxml");

        sceneSwitchController.switchScene(SceneSwitchController.SceneType.LoginScene);

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
