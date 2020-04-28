package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.models.UserModel;
import main.models.UserType;
import services.ServiceInjector;
import services.UserExistsException;
import services.UserService;

public class JavaFXBoot extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceInjector.getInstance().getFileSystemManager().createJSONFiles();
        UserService userService = ServiceInjector.getInstance().getUserService();

        try {
            userService.createUser(new UserModel("email@example.com", "encrypted pass", "visible name", UserType.Manager));
        } catch (UserExistsException e) {
            e.printStackTrace();
            System.out.println(userService.validateUserCredentials("email@example.com", "encrypted pass"));
        }

        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
