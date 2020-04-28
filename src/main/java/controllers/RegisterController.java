package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.SceneController;
import models.UserModel;
import models.UserType;
import services.ServiceInjector;
import services.UserExistsException;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField passwordTextField;

    @FXML
    public TextField confirmPasswordTextField;

    @FXML
    public Button createAccountButton;

    @FXML
    public Button goToLoginPage;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceInjector.getInstance().getUserService();
    }

    public void onGoToLoginPageClick(ActionEvent actionEvent) {
        SceneController.getInstance().switchScene(SceneController.SceneType.MainScene);
    }

    public void onCreateAccountClick(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty()) {
            System.out.println("field is required");
            return;
        }
        if (emailTextField.getText().isEmpty()) {
            System.out.println("field is required");

            return;
        }
        if (validateEmailAddress(emailTextField.getText())) {
            System.out.println("invalid email address");

            return;
        }

        if (passwordTextField.getText().isEmpty()) {
            System.out.println("field is required");

            return;
        }

        if (confirmPasswordTextField.getText().isEmpty()) {
            System.out.println("field is required");

            return;
        }

        if (!validateEqualPasswordsFields()) {
            System.out.println("password don't match");

            return;
        }

        if (validateCredentials()) {
            System.out.println("Account already exists");
            // user exists
            return;
        }

        try {
            userService.createUser(new UserModel(emailTextField.getText(), passwordTextField.getText(), nameTextField.getText(), UserType.RegularUser));

            // todo redirect user to edit profile
        } catch (UserExistsException e) {
            System.out.println("Account already exists");
            // user exists
        }
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

    private boolean validateEqualPasswordsFields() {
        return passwordTextField.getText().equals(confirmPasswordTextField.getText());
    }

    private boolean validateCredentials() {
        return userService.validateUserCredentials(emailTextField.getText(), passwordTextField.getText());
    }
}
