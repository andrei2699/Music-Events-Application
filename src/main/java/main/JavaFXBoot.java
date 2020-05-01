package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BarModel;
import models.UserModel;
import models.UserType;
import services.BarService;
import services.ServiceInjector;

public class JavaFXBoot extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceInjector.getInstance().getFileSystemManager().createJSONFiles();
        BarService barService = ServiceInjector.getInstance().getBarService();

        barService.createBar(new BarModel(2, "Bar Name", "Bars Address"));

        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("/main.fxml")));
        Scene registerScene = new Scene(FXMLLoader.load(getClass().getResource("/register.fxml")));

        // todo replace with login.fxml
        Scene loginScene = new Scene(FXMLLoader.load(getClass().getResource("/main.fxml")));

        SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();

        sceneSwitchController.setStage(primaryStage);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.MainScene, mainScene);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.RegisterScene, registerScene);
        sceneSwitchController.addScene(SceneSwitchController.SceneType.LoginScene, loginScene);

        primaryStage.setScene(registerScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
