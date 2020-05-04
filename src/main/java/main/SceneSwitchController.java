package main;

import controllers.ChangeableSceneController;
import javafx.fxml.FXMLLoader;
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
        EditArtistProfileScene
    }

    private static final SceneSwitchController instance = new SceneSwitchController();

    private Stage stage;
    private final Map<SceneType, ControllerScenePair> sceneMap = new HashMap<>();

    public static SceneSwitchController getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addScene(SceneType type, String pathToFXMLFile) throws IOException {
        sceneMap.put(type, new ControllerScenePair());
        sceneMap.get(type).setScene(new Scene(FXMLLoader.load(getClass().getResource(pathToFXMLFile))));
    }

    public void addControllerToScene(SceneType type, ChangeableSceneController controller) {
        if (sceneMap.containsKey(type)) {
            sceneMap.get(type).setController(controller);
        }
    }

    public Scene getScene(SceneType type) {
        return sceneMap.get(type).getScene();
    }

    public void switchScene(SceneType type) {
        if (sceneMap.containsKey(type)) {
            stage.setTitle(type.toString());
            ControllerScenePair controllerScenePair = sceneMap.get(type);
            stage.setScene(controllerScenePair.getScene());
            if (controllerScenePair.hasController()) {
                controllerScenePair.getController().onSceneChanged();
            }
        }
    }

    private static class ControllerScenePair {
        private Scene scene;
        private ChangeableSceneController controller;


        public Scene getScene() {
            return scene;
        }

        public ChangeableSceneController getController() {
            return controller;
        }

        public void setController(ChangeableSceneController controller) {
            this.controller = controller;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }

        public boolean hasController() {
            return controller != null;
        }
    }
}
