package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.LoggedUserData;
import main.SceneSwitchController;
import services.IUserService;
import services.ServiceProvider;
import utils.StringValidator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static main.ApplicationResourceStrings.INVALID_EMAIL_ERROR_MESSAGE;
import static main.ApplicationResourceStrings.REQUIRED_FIELD_ERROR_MESSAGE;

public class LoginController implements Initializable {
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

    private IUserService userService;

    //pentru apelul prin reflexie
    public LoginController() {
    }

    //pentru testare
    protected LoginController(IUserService iUserService) {
        userService = iUserService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceProvider.getUserService();
        LoggedUserData.getInstance().setUserModel(null);
        setAllLabelsInvisible();
    }

    @FXML
    public void onLoginClick(ActionEvent actionEvent) {
        setAllLabelsInvisible();
        boolean canLogin = true;

        if (StringValidator.isStringEmpty(emailTextField.getText())) {
            emailErrorLabel.setText(REQUIRED_FIELD_ERROR_MESSAGE);
            emailErrorLabel.setVisible(true);
            canLogin = false;
        }

        if (!validateEmailAddress(emailTextField.getText())) {
            emailErrorLabel.setText(INVALID_EMAIL_ERROR_MESSAGE);
            emailErrorLabel.setVisible(true);
            canLogin = false;
        }

        if (StringValidator.isStringEmpty(passwordTextField.getText())) {
            passwordErrorLabel.setVisible(true);
            canLogin = false;
        }

        if (canLogin) {
            if (userService.validateUserCredentials(emailTextField.getText(), passwordTextField.getText())) {
                LoggedUserData.getInstance().setUserModel(userService.getUser(emailTextField.getText()));
                onSkipPageButtonClick(actionEvent);
            } else {
                incorrectCredentialsError.setVisible(true);
            }
        }
    }

    @FXML
    public void onGoToRegisterPageClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchToRegisterScene();
    }

    @FXML
    public void onSkipPageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchToMainScene();
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
