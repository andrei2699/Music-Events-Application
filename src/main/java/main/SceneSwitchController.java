package main;

import controllers.ChangeableSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SceneSwitchController {

    public enum SceneType {
        MainScene,
        RegisterScene,
        LoginScene,
        BarProfileScene,
        ArtistProfileScene,
        EditBarProfileScene,
        EditArtistProfileScene,
        CreateEventFormScene
    }

    private static final SceneSwitchController instance = new SceneSwitchController();

    private Stage stage;
    private final Map<SceneType, String> sceneMap = new HashMap<>();

    public static SceneSwitchController getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addScene(SceneType type, String pathToFXMLFile) {
        sceneMap.put(type, pathToFXMLFile);
    }

    public void switchScene(SceneType type) {
        if (sceneMap.containsKey(type)) {
            stage.setTitle(type.toString());
            String path = sceneMap.get(type);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
//                scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (loader.getController() instanceof ChangeableSceneController) {
                ChangeableSceneController controller = loader.getController();
                controller.onSceneChanged();
            }
        }
    }

    public Stage getStage() {
        return stage;
    }
}
