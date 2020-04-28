package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.ServiceInjector;

public class JavaFXBoot extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ServiceInjector.getInstance().getFileSystemManager().createJSONFiles();

        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
