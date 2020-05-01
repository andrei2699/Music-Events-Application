package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.SceneSwitchController;
import services.ServiceInjector;
import services.UserService;

public class LoginController {

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public Label incorrectCredentialsError;


    public void onLoginClick(ActionEvent actionEvent) {
        UserService userService = ServiceInjector.getInstance().getUserService();
        if(userService.validateUserCredentials(emailTextField.getText(), passwordTextField.getText()))
            onSkipPageButtonClick(actionEvent);
    }

    public void onGoToRegisterPageClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.RegisterScene);
    }

    public void onSkipPageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }
}
