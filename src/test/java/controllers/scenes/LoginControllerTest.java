package controllers.scenes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.SceneSwitchController;
import org.assertj.core.api.AssertFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IUserService;

import static models.other.UserType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends ApplicationTest {
    private static final String EMAIL = "email@yahoo.com";
    private static final String PASSWORD = "password";
    private static final String WRONG_EMAIL_NO_AT = "wrongEmail.com";
    private static final String WRONG_EMAIL_NO_COM = "wrongEmail@gmail";
    private static final String WRONG_EMAIL = "email";

    LoginController loginController;
    @Mock
    private IUserService userService;

    @Before
    public void setUp() {
        loginController = new LoginController(userService);
        loginController.emailTextField = new TextField();
        loginController.passwordTextField = new PasswordField();

        loginController.emailErrorLabel = new Label();
        loginController.passwordErrorLabel = new Label();
        loginController.incorrectCredentialsError = new Label();
    }

    @Test
    public void testInitialize() {
        loginController.initialize(null, null);

        assertFalse(loginController.passwordErrorLabel.isVisible());
        assertFalse(loginController.emailErrorLabel.isVisible());
        assertFalse(loginController.incorrectCredentialsError.isVisible());
    }

    @Test
    public void testOnLoginClickAllEmpty() {
        loginController.emailTextField.setText(" ");
        loginController.passwordTextField.setText("");

        loginController.onLoginClick(null);

        assertTrue(loginController.emailErrorLabel.isVisible());
        assertTrue(loginController.passwordErrorLabel.isVisible());
        assertFalse(loginController.incorrectCredentialsError.isVisible());
    }

    @Test
    public void testOnLoginClickInvalidEmailNoAt() {
        loginController.emailTextField.setText(WRONG_EMAIL_NO_AT);
        loginController.passwordTextField.setText(PASSWORD);

        loginController.onLoginClick(null);

        assertTrue(loginController.emailErrorLabel.isVisible());
        assertFalse(loginController.passwordErrorLabel.isVisible());
        assertFalse(loginController.incorrectCredentialsError.isVisible());
    }

    @Test
    public void testOnLoginClickInvalidEmailNoCom() {
        loginController.emailTextField.setText(WRONG_EMAIL_NO_COM);
        loginController.passwordTextField.setText(PASSWORD);

        loginController.onLoginClick(null);

        assertFalse(loginController.passwordErrorLabel.isVisible());
        assertTrue(loginController.emailErrorLabel.isVisible());
        assertFalse(loginController.incorrectCredentialsError.isVisible());
    }

    @Test
    public void testOnLoginClickInvalidEmailAndNoPassword() {
        loginController.emailTextField.setText(null);
        loginController.passwordTextField.setText(PASSWORD);

        loginController.onLoginClick(null);

        assertTrue(loginController.emailErrorLabel.isVisible());
    }

    @Test
    public void testOnLoginClickInvalidUserCredentials() {
        loginController.emailTextField.setText(EMAIL);
        loginController.passwordTextField.setText(PASSWORD);

        when(userService.validateUserCredentials(loginController.emailTextField.getText(),loginController.passwordTextField.getText())).thenReturn(false);
        loginController.onLoginClick(null);

        assertTrue(loginController.incorrectCredentialsError.isVisible());
        assertFalse(loginController.emailErrorLabel.isVisible());
        assertFalse(loginController.passwordErrorLabel.isVisible());
    }

    @Test
    public void testOnLoginClickValidUserCredentials() {
        loginController.emailTextField.setText(EMAIL);
        loginController.passwordTextField.setText(PASSWORD);

        when(userService.validateUserCredentials(loginController.emailTextField.getText(),loginController.passwordTextField.getText())).thenReturn(true);
        loginController.onLoginClick(null);

        assertFalse(loginController.incorrectCredentialsError.isVisible());
        assertFalse(loginController.emailErrorLabel.isVisible());
        assertFalse(loginController.passwordErrorLabel.isVisible());
    }

}