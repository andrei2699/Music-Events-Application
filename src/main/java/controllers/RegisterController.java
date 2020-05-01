package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.SceneSwitchController;
import models.UserModel;
import models.UserType;
import services.ServiceInjector;
import services.UserExistsException;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    private static final String REQUIRED_FIELD_ERROR_MESSAGE = "* Camp Obligatoriu";
    private static final String INVALID_EMAIL_ERROR_MESSAGE = "* Adresa de email invalida";
    private static final String PASSWORDS_DONT_MATCH_ERROR_MESSAGE = "* Cele doua parole nu sunt identice";

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public PasswordField passwordTextField;

    @FXML
    public PasswordField confirmPasswordTextField;

    @FXML
    public ComboBox<UserType> userTypeComboBox;

    @FXML
    public Button createAccountButton;

    @FXML
    public Button goToLoginPage;

    @FXML
    public Label nameErrorLabel;

    @FXML
    public Label emailErrorLabel;

    @FXML
    public Label passwordErrorLabel;

    @FXML
    public Label confirmPasswordErrorLabel;

    @FXML
    public Label emailInUseErrorLabel;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceInjector.getInstance().getUserService();
        setAllLabelsInvisible();
        userTypeComboBox.setItems(FXCollections.observableArrayList(UserType.values()));
        userTypeComboBox.getSelectionModel().select(0);
    }

    public void onGoToLoginPageClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.LoginScene);
    }

    public void onCreateAccountClick(ActionEvent actionEvent) {
        setAllLabelsInvisible();

        if (nameTextField.getText().isEmpty()) {
            showErrorLabel(nameErrorLabel);
            return;
        }
        if (emailTextField.getText().isEmpty()) {
            showErrorLabel(emailErrorLabel, REQUIRED_FIELD_ERROR_MESSAGE);
            return;
        }
        if (!validateEmailAddress(emailTextField.getText())) {
            showErrorLabel(emailErrorLabel, INVALID_EMAIL_ERROR_MESSAGE);
            return;
        }

        if (passwordTextField.getText().isEmpty()) {
            showErrorLabel(passwordErrorLabel);
            return;
        }

        if (confirmPasswordTextField.getText().isEmpty()) {
            showErrorLabel(confirmPasswordErrorLabel, REQUIRED_FIELD_ERROR_MESSAGE);
            return;
        }

        if (!validateEqualPasswordsFields()) {
            showErrorLabel(confirmPasswordErrorLabel, PASSWORDS_DONT_MATCH_ERROR_MESSAGE);
            return;
        }

        if (validateCredentials()) {
            showErrorLabel(emailInUseErrorLabel);
            return;
        }

//        try {
//            UserType userType = userTypeComboBox.getValue();
//            userService.createUser(new UserModel(emailTextField.getText(), passwordTextField.getText(), nameTextField.getText(), userType));
//
//            // todo redirect user to edit profile
//            // todo add messaging system between scenes
////            if (userType == UserType.Artist) {
////                SceneSwitchController.getInstance().switchScene(ArtistProfileScene);
////            } else if (userType == UserType.Manager) {
////                SceneSwitchController.getInstance().switchScene(BarProfileScene);
////            }
//        } catch (UserExistsException e) {
//            showErrorLabel(emailInUseErrorLabel);
//        }
    }

    public void onSkipPageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
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

    private void setAllLabelsInvisible() {
        nameErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        confirmPasswordErrorLabel.setVisible(false);
        emailInUseErrorLabel.setVisible(false);
    }

    private void showErrorLabel(Label label, String text) {
        label.setVisible(true);
        label.setText(text);
    }

    private void showErrorLabel(Label label) {
        label.setVisible(true);
    }
}
