package main;

import controllers.components.MakeReservationPopupWindowController;
import controllers.scenes.ChangeableSceneWithUserModelController;
import controllers.scenes.ISceneResponseCall;
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
        ViewBarProfileScene,
        ViewArtistProfileScene,
        EditBarProfileScene,
        EditArtistProfileScene,
        CreateEventFormScene
    }

    private static final SceneSwitchController instance = new SceneSwitchController();

    private Stage primaryStage;
    private Stage reservationPopupStage;

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
        getControllerFromSwitchedScene(type);
    }

    public void switchScene(SceneType type, Integer userModelId) {
        Object controller = getControllerFromSwitchedScene(type);

        if (controller instanceof ChangeableSceneWithUserModelController) {
            ChangeableSceneWithUserModelController changeableSceneController = (ChangeableSceneWithUserModelController) controller;

            changeableSceneController.onSetUserModelId(userModelId);
        }
    }

    public void showReservationPopup(int maximumNumberOfSeats, ISceneResponseCall<Integer> responseCall) {

        closeReservationPopup();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/makeReservationPopupWindow.fxml"));

        MakeReservationPopupWindowController controller = new MakeReservationPopupWindowController();
        controller.setPopupResponseCall(responseCall);
        controller.setMaximumNumberOfSeats(maximumNumberOfSeats);
        loader.setController(controller);

        try {
            Parent root = loader.load();
            reservationPopupStage = new Stage();
            reservationPopupStage.setResizable(false);
            reservationPopupStage.centerOnScreen();
            reservationPopupStage.setTitle("Rezervare");
            reservationPopupStage.setScene(new Scene(root));
            reservationPopupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeReservationPopup() {
        if (reservationPopupStage != null) {
            reservationPopupStage.close();
            reservationPopupStage = null;
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
