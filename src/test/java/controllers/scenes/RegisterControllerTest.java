package controllers.scenes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IUserService;
import services.implementation.UserExistsException;

import static main.ApplicationResourceStrings.*;
import static models.other.UserType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest extends ApplicationTest {
    private static final String VALID_NAME = "Valid Name";
    private static final String PASSWORD = "password";
    private static final String WRONG_PASSWORD = "wrongPassword";
    private static final String EMAIL = "email@yahoo.com";
    private static final String WRONG_EMAIL_NO_AT = "wrongEmail.com";
    private static final String WRONG_EMAIL_NO_COM = "wrongEmail@gmail";

    private RegisterController registerController;
    @Mock
    private IUserService userService;

    @Before
    public void setUp() {
        registerController = new RegisterController(userService);
        registerController.nameTextField = new TextField();
        registerController.passwordTextField = new PasswordField();
        registerController.confirmPasswordTextField = new PasswordField();
        registerController.emailTextField = new TextField();

        registerController.userTypeComboBox = new ComboBox<>();

        registerController.nameErrorLabel = new Label();
        registerController.passwordErrorLabel = new Label();
        registerController.confirmPasswordErrorLabel = new Label();
        registerController.emailErrorLabel = new Label();
        registerController.emailInUseErrorLabel = new Label();

        registerController.userTypeComboBox.setValue(Artist);
    }

    @Test
    public void testInitialize() {
        registerController.initialize(null, null);

        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());

        assertEquals(3, registerController.userTypeComboBox.getItems().size());
        assertEquals(RegularUser, registerController.userTypeComboBox.getItems().get(0));
        assertEquals(Manager, registerController.userTypeComboBox.getItems().get(1));
        assertEquals(Artist, registerController.userTypeComboBox.getItems().get(2));
    }

    @Test
    public void testOnCreateAccountClickAllEmpty() {
        registerController.nameTextField.setText("     ");
        registerController.emailTextField.setText(" ");
        registerController.passwordTextField.setText("");
        registerController.confirmPasswordTextField.setText("   ");

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);
        registerController.onCreateAccountClick(null);

        assertTrue(registerController.nameErrorLabel.isVisible());
        assertTrue(registerController.emailErrorLabel.isVisible());
        assertTrue(registerController.passwordErrorLabel.isVisible());
        assertTrue(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountClickDifferentPasswords() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(null);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(WRONG_PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);
        registerController.onCreateAccountClick(null);

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertTrue(registerController.emailErrorLabel.isVisible());
        assertEquals(REQUIRED_FIELD_ERROR_MESSAGE, registerController.emailErrorLabel.getText());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertTrue(registerController.confirmPasswordErrorLabel.isVisible());
        assertEquals(PASSWORDS_DONT_MATCH_ERROR_MESSAGE, registerController.confirmPasswordErrorLabel.getText());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountEmailExists() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(EMAIL);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(true);
        registerController.onCreateAccountClick(null);

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertTrue(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountNameEmptyAndDifferentPasswords() {
        registerController.nameTextField.setText("  ");
        registerController.emailTextField.setText(EMAIL);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(WRONG_PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);
        registerController.onCreateAccountClick(null);

        assertTrue(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertTrue(registerController.confirmPasswordErrorLabel.isVisible());
        assertEquals(PASSWORDS_DONT_MATCH_ERROR_MESSAGE, registerController.confirmPasswordErrorLabel.getText());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountNameInvalidEmailNoAtAndEmptyPassword() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(WRONG_EMAIL_NO_AT);
        registerController.passwordTextField.setText("  ");
        registerController.confirmPasswordTextField.setText(PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);
        registerController.onCreateAccountClick(null);

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertTrue(registerController.emailErrorLabel.isVisible());
        assertEquals(INVALID_EMAIL_ERROR_MESSAGE, registerController.emailErrorLabel.getText());
        assertTrue(registerController.passwordErrorLabel.isVisible());
        assertTrue(registerController.confirmPasswordErrorLabel.isVisible());
        assertEquals(PASSWORDS_DONT_MATCH_ERROR_MESSAGE, registerController.confirmPasswordErrorLabel.getText());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountNameInvalidEmailNoCom() {
        registerController.nameTextField.setText("");
        registerController.emailTextField.setText(WRONG_EMAIL_NO_COM);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);
        registerController.onCreateAccountClick(null);

        assertTrue(registerController.nameErrorLabel.isVisible());
        assertTrue(registerController.emailErrorLabel.isVisible());
        assertEquals(INVALID_EMAIL_ERROR_MESSAGE, registerController.emailErrorLabel.getText());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountValidRegularUser() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(EMAIL);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(PASSWORD);
        registerController.userTypeComboBox.setValue(RegularUser);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);

        try {
            UserModel userModel = new UserModel(23, EMAIL, PASSWORD, VALID_NAME, RegularUser);
            when(userService.createUser(EMAIL, PASSWORD, VALID_NAME, RegularUser))
                    .thenReturn(userModel);
            registerController.onCreateAccountClick(null);
        } catch (UserExistsException e) {
            fail("User Exists");
        }

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountValidManager() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(EMAIL);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(PASSWORD);
        registerController.userTypeComboBox.setValue(Manager);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);

        try {
            UserModel userModel = new UserModel(23, EMAIL, PASSWORD, VALID_NAME, Manager);
            when(userService.createUser(EMAIL, PASSWORD, VALID_NAME, Manager))
                    .thenReturn(userModel);
            registerController.onCreateAccountClick(null);
        } catch (UserExistsException e) {
            fail("User Exists");
        }

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }

    @Test
    public void testOnCreateAccountValidArtist() {
        registerController.nameTextField.setText(VALID_NAME);
        registerController.emailTextField.setText(EMAIL);
        registerController.passwordTextField.setText(PASSWORD);
        registerController.confirmPasswordTextField.setText(PASSWORD);

        when(userService.existsUser(registerController.emailTextField.getText())).thenReturn(false);

        try {
            UserModel userModel = new UserModel(23, EMAIL, PASSWORD, VALID_NAME, Artist);
            when(userService.createUser(EMAIL, PASSWORD, VALID_NAME, Artist))
                    .thenReturn(userModel);
            registerController.onCreateAccountClick(null);
        } catch (UserExistsException e) {
            fail("User Exists");
        }

        assertFalse(registerController.nameErrorLabel.isVisible());
        assertFalse(registerController.emailErrorLabel.isVisible());
        assertFalse(registerController.passwordErrorLabel.isVisible());
        assertFalse(registerController.confirmPasswordErrorLabel.isVisible());
        assertFalse(registerController.emailInUseErrorLabel.isVisible());
    }
}
