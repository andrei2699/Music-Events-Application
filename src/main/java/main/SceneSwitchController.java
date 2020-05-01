package main;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public final class SceneSwitchController {
    public enum SceneType {
        MainScene,
        RegisterScene,
        LoginScene,
        ProfileScene
    }

    private static final SceneSwitchController instance = new SceneSwitchController();

    private Stage stage;
    private final Map<SceneType, Scene> sceneMap = new HashMap<>();

    public static SceneSwitchController getInstance() {
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
