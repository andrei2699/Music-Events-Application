package main;

import controllers.components.CrashReportPopupWindowController;
import controllers.components.MakeReservationPopupWindowController;
import controllers.scenes.ChangeableSceneWithModelController;
import controllers.scenes.ISceneResponseCall;
import controllers.scenes.ISwitchSceneListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.ApplicationResourceStrings.*;

public final class SceneSwitchController {

    public enum SceneType {
        MainScene,
        RegisterScene,
        LoginScene,
        MainSceneContent,
        ViewBarProfileContentScene,
        ViewArtistProfileContentScene,
        ViewRegularUserContentScene,
        EditBarProfileContentScene,
        EditArtistProfileContentScene,
        CreateEventFormContentScene,
        ChatContentScene
    }

    private static final SceneSwitchController instance = new SceneSwitchController();

    private Stage primaryStage;
    private Stage reservationPopupStage;
    private Stage crashReportPopupStage;
    private Pane mainPageContentPane;
    private final List<ISwitchSceneListener> onSwitchSceneListeners = new ArrayList<>();

    private final Map<SceneType, String> sceneMap = new HashMap<>();

    public static SceneSwitchController getInstance() {
        return instance;
    }

    public void setMainPageContentPane(Pane pageContent) {
        mainPageContentPane = pageContent;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void addScene(SceneType type, String pathToFXMLFile) {
        sceneMap.put(type, pathToFXMLFile);
    }

    public void addOnSceneSwitchListener(ISwitchSceneListener callListener) {
        if (!onSwitchSceneListeners.contains(callListener)) {
            onSwitchSceneListeners.add(callListener);
        }
    }

    public void loadFXMLToMainPage(SceneType sceneType) {
        changeMainPageContent(sceneType);
    }

    public void loadFXMLToMainPage(SceneType sceneType, Integer modelId) {
        Object controller = changeMainPageContent(sceneType);

        if (controller instanceof ChangeableSceneWithModelController) {
            ChangeableSceneWithModelController changeableSceneController = (ChangeableSceneWithModelController) controller;

            changeableSceneController.onSetModelId(modelId);
        }
    }

    public void switchToMainScene(SceneType mainSceneContent) {
        switchScene(SceneType.MainScene);
        loadFXMLToMainPage(mainSceneContent);
    }

    public void switchToMainScene() {
        switchToMainScene(SceneType.MainSceneContent);
    }

    public void switchToRegisterScene() {
        switchScene(SceneType.RegisterScene);
    }

    public void switchToLoginScene() {
        switchScene(SceneType.LoginScene);
    }

    public void showReservationPopup(int maximumNumberOfSeats, ISceneResponseCall<Integer> responseCall) {
        closeReservationPopup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAKE_RESERVATION_POPUP_WINDOW_FXML_PATH));

        MakeReservationPopupWindowController controller = new MakeReservationPopupWindowController();
        controller.setPopupResponseCall(responseCall);
        controller.setMaximumNumberOfSeats(maximumNumberOfSeats);
        loader.setController(controller);

        try {
            reservationPopupStage = new Stage();
            showPopup(reservationPopupStage, loader.load(), RESERVATION_TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCrashPopup(String crashMessage) {
        closeCrashReportPopup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CRASH_REPORT_POPUP_WINDOW_FXML_PATH));

        CrashReportPopupWindowController controller = new CrashReportPopupWindowController();
        controller.setCrashMessage(crashMessage);
        loader.setController(controller);

        try {
            crashReportPopupStage = new Stage();
            showPopup(crashReportPopupStage, loader.load(), CRASH_TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeCrashReportPopup() {
        closePopup(crashReportPopupStage);
    }

    public void closeReservationPopup() {
        closePopup(reservationPopupStage);
    }

    private Object changeMainPageContent(SceneType sceneType) {
        FXMLLoader fxmlLoader = loadFXML(sceneType);
        Parent root;
        if (fxmlLoader != null) {
            triggerOnSwitchSceneListeners();

            root = fxmlLoader.getRoot();
            mainPageContentPane.getChildren().clear();
            mainPageContentPane.getChildren().add(root);
            return fxmlLoader.getController();
        }
        return null;
    }

    private FXMLLoader loadFXML(SceneType type) {
        if (sceneMap.containsKey(type)) {

            String path = sceneMap.get(type);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loader;
        }
        return null;
    }

    private void switchScene(SceneType type) {
        FXMLLoader fxmlLoader = loadFXML(type);
        if (fxmlLoader != null) {
            triggerOnSwitchSceneListeners();
            Scene scene = new Scene(fxmlLoader.getRoot());
            primaryStage.setScene(scene);
        }
    }

    private void triggerOnSwitchSceneListeners() {
        for (ISwitchSceneListener listener : onSwitchSceneListeners) {
            if (listener != null) {
                listener.onSceneSwitched();
            }
        }
    }

    private void showPopup(Stage popupStage, Parent root, String title) {
        popupStage.setTitle(title);
        popupStage.setResizable(false);
        popupStage.centerOnScreen();
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    private void closePopup(Stage popupStage) {
        if (popupStage != null) {
            popupStage.close();
        }
    }
}
