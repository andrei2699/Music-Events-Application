package controllers.scenes;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;
import models.other.UserType;
import services.ServiceProvider;
import services.IUserService;
import services.implementation.UserExistsException;
import utils.StringValidator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static main.ApplicationResourceStrings.*;
import static main.SceneSwitchController.SceneType.*;

public class RegisterController implements Initializable {

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

    private IUserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceProvider.getUserService();

        setAllLabelsInvisible();
        userTypeComboBox.setItems(FXCollections.observableArrayList(UserType.values()));
        userTypeComboBox.getSelectionModel().select(0);
    }

    public void onGoToLoginPageClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchToLoginScene();
    }

    public void onCreateAccountClick(ActionEvent actionEvent) {
        setAllLabelsInvisible();
        boolean canCreateAccount = true;

        if (StringValidator.isStringEmpty(nameTextField.getText())) {
            showErrorLabel(nameErrorLabel);
            canCreateAccount = false;
        }
        if (StringValidator.isStringEmpty(emailTextField.getText())) {
            showErrorLabel(emailErrorLabel, REQUIRED_FIELD_ERROR_MESSAGE);
            canCreateAccount = false;
        }
        if (!validateEmailAddress(emailTextField.getText())) {
            showErrorLabel(emailErrorLabel, INVALID_EMAIL_ERROR_MESSAGE);
            canCreateAccount = false;
        }

        if (StringValidator.isStringEmpty(passwordTextField.getText())) {
            showErrorLabel(passwordErrorLabel);
            canCreateAccount = false;
        }

        if (StringValidator.isStringEmpty(confirmPasswordTextField.getText())) {
            showErrorLabel(confirmPasswordErrorLabel, REQUIRED_FIELD_ERROR_MESSAGE);
            canCreateAccount = false;
        }

        if (!validateEqualPasswordsFields()) {
            showErrorLabel(confirmPasswordErrorLabel, PASSWORDS_DONT_MATCH_ERROR_MESSAGE);
            canCreateAccount = false;
        }

        if (emailExists()) {
            showErrorLabel(emailInUseErrorLabel);
            canCreateAccount = false;
        }

        if (canCreateAccount) {
            try {
                UserModel user = userService.createUser(emailTextField.getText().trim(), passwordTextField.getText().trim(), nameTextField.getText().trim(), userTypeComboBox.getValue());
                LoggedUserData.getInstance().setUserModel(user);

                if (user.getType() == UserType.Manager) {
                    SceneSwitchController.getInstance().switchToMainScene(EditBarProfileContentScene);
                } else if (user.getType() == UserType.Artist) {
                    SceneSwitchController.getInstance().switchToMainScene(EditArtistProfileContentScene);
                } else {
                    SceneSwitchController.getInstance().switchToMainScene();
                }
            } catch (UserExistsException e) {
                showErrorLabel(emailInUseErrorLabel);
            }
        }
    }

    public void onSkipPageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchToMainScene();
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

    private boolean emailExists() {
        return userService.existsUser(emailTextField.getText());
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
