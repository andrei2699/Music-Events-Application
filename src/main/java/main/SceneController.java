package main;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public final class SceneController {
    public enum SceneType {
        MainScene,
        RegisterScene,
        LoginScene
    }

    private static SceneController instance = new SceneController();

    private Stage stage;
    private Map<SceneType, Scene> sceneMap = new HashMap<>();

    public static SceneController getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addScene(SceneType type, Scene scene) {
        sceneMap.put(type, scene);
    }

    public Scene getScene(SceneType type) {
        return sceneMap.get(type);
    }

    public void switchScene(SceneType type) {
        if (sceneMap.containsKey(type)) {
            stage.setScene(sceneMap.get(type));
        }
    }
}
