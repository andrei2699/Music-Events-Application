package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.BarModel;
import models.UserModel;
import models.UserType;
import services.BarService;
import services.ServiceInjector;
import services.UserExistsException;
import services.UserService;

public class JavaFXBoot extends Application {

    private Scene registerScene;
    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceInjector.getInstance().getFileSystemManager().createJSONFiles();
        BarService barService = ServiceInjector.getInstance().getBarService();

        barService.createBar(new BarModel(2, "Bar Name", "Bars Address"));

        mainScene = new Scene(FXMLLoader.load(getClass().getResource("/main.fxml")));
        registerScene = new Scene(FXMLLoader.load(getClass().getResource("/register.fxml")));

        SceneController sceneController = SceneController.getInstance();

        sceneController.setStage(primaryStage);
        sceneController.addScene(SceneController.SceneType.MainScene, mainScene);
        sceneController.addScene(SceneController.SceneType.RegisterScene, registerScene);

        primaryStage.setScene(registerScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
