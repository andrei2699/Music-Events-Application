package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.SceneSwitchController;
import services.ServiceInjector;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    private static final String REQUIRED_FIELD_ERROR_MESSAGE = "* Camp Obligatoriu";
    private static final String INVALID_EMAIL_ERROR_MESSAGE = "* Adresa de email invalida";

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public Label incorrectCredentialsError;

    @FXML
    public Label emailErrorLabel;

    @FXML
    public Label passwordErrorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllLabelsInvisible();
    }

    public void onLoginClick(ActionEvent actionEvent) {
        setAllLabelsInvisible();

        if (emailTextField.getText().isEmpty()) {
            emailErrorLabel.setText(REQUIRED_FIELD_ERROR_MESSAGE);
            emailErrorLabel.setVisible(true);
            return;
        }

        if (!validateEmailAddress(emailTextField.getText())) {
            emailErrorLabel.setText(INVALID_EMAIL_ERROR_MESSAGE);
            emailErrorLabel.setVisible(true);
            return;
        }

        if (passwordTextField.getText().isEmpty()) {
            passwordErrorLabel.setVisible(true);
            return;
        }

        UserService userService = ServiceInjector.getInstance().getUserService();
        if (userService.validateUserCredentials(emailTextField.getText(), passwordTextField.getText())) {
            onSkipPageButtonClick(actionEvent);
        } else {
            incorrectCredentialsError.setVisible(true);
        }
    }

    public void onGoToRegisterPageClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.RegisterScene);
    }

    public void onSkipPageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }

    private void setAllLabelsInvisible() {
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        incorrectCredentialsError.setVisible(false);
    }

    private boolean validateEmailAddress(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
