package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BarModel;
import services.BarService;
import services.ServiceProvider;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceProvider.getFileSystemManager().createJSONFiles();
        BarService barService = ServiceProvider.getBarService();

        barService.createBar(new BarModel(2, "Bar Name", "Bars Address"));

        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("/barProfilePage.fxml")));
        Scene registerScene = new Scene(FXMLLoader.load(getClass().getResource("/register.fxml")));

        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/login.fxml")));

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, mainScene);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, registerScene);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, loginScene);

        sceneSwitchController.switchScene(SceneSwitchController.SceneType.LoginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
