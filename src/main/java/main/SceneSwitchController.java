package main;

import controllers.ChangeableSceneController;
import controllers.IResponseCall;
import controllers.MakeReservationPopupWindowController;
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

    private Stage primaryStage;
    private final Map<SceneType, String> sceneMap = new HashMap<>();

    public static SceneSwitchController getInstance() {
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addScene(SceneType type, String pathToFXMLFile) {
        sceneMap.put(type, pathToFXMLFile);
    }

    public void switchScene(SceneType type) {
        Object controller = getControllerFromSwitchedScene(type);

        if (controller instanceof ChangeableSceneController) {
            ((ChangeableSceneController) controller).onSceneChanged();
        }
    }

    public void switchScene(SceneType type, Integer userModelId) {
        Object controller = getControllerFromSwitchedScene(type);

        if (controller instanceof ChangeableSceneController) {
            ChangeableSceneController changeableSceneController = (ChangeableSceneController) controller;

            changeableSceneController.setUserModelId(userModelId);
            changeableSceneController.onSceneChanged();
        }
    }

    public void showReservationPopup(int maximumNumberOfSeats, IResponseCall<Integer> responseCall) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/makeReservationPopupWindow.fxml"));

        MakeReservationPopupWindowController controller = new MakeReservationPopupWindowController();
        controller.setPopupResponseCall(responseCall);
        controller.setMaximumNumberOfSeats(maximumNumberOfSeats);
        loader.setController(controller);

        try {
            Parent root = loader.load();
            Stage reservationPopupStage = new Stage();
            reservationPopupStage.centerOnScreen();
            reservationPopupStage.setTitle("Rezervare");
            reservationPopupStage.setScene(new Scene(root));
            reservationPopupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private Object getControllerFromSwitchedScene(SceneType type) {
        if (sceneMap.containsKey(type)) {
            primaryStage.setTitle(type.toString());
            String path = sceneMap.get(type);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loader.getController();
        }
        return null;
    }
}
