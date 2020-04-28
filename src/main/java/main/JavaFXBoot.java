package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.UserModel;
import models.UserType;
import services.ServiceInjector;
import services.UserExistsException;
import services.UserService;

public class JavaFXBoot extends Application {

    private Scene registerScene;
    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceInjector.getInstance().getFileSystemManager().createJSONFiles();
//        UserService userService = ServiceInjector.getInstance().getUserService();

//        try {
//            userService.createUser(new UserModel("email@example.com", "encrypted pass", "visible name", UserType.Manager));
//        } catch (UserExistsException e) {
//            e.printStackTrace();
//            System.out.println(userService.validateUserCredentials("email@example.com", "encrypted pass"));
//        }

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
