package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import services.ServiceProvider;

import static main.ApplicationResourceStrings.*;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) {

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setPrimaryStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, MAIN_SCENE_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainSceneContent, MAIN_SCENE_CONTENT_FXML_PATH);

        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, REGISTER_SCENE_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, LOGIN_FXML_PATH);

        sceneSwitchController.addScene(SceneSwitchController.SceneType.ViewBarProfileContentScene, VIEW_BAR_PROFILE_SCENE_CONTENT_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.ViewArtistProfileContentScene, VIEW_ARTIST_PROFILE_SCENE_CONTENT_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.ViewRegularUserContentScene, VIEW_REGULAR_USER_SCENE_CONTENT_FXML_PATH);

        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditBarProfileContentScene, EDIT_BAR_SCENE_CONTENT_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.EditArtistProfileContentScene, EDIT_ARTIST_SCENE_CONTENT_FXML_PATH);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.CreateEventFormContentScene, CREATE_EVENT_SCENE_CONTENT_FXML_PATH);

        sceneSwitchController.switchToLoginScene();

        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
