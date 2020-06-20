package controllers.scenes;

import controllers.components.scheduleGrid.ReadonlyScheduleLoadStrategy;
import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.BarModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IBarService;
import services.IUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditBarProfilePageControllerTest extends ApplicationTest {

    EditBarProfilePageController editBarProfilePageController;

    @Mock
    IUserService userService;
    @Mock
    IBarService barService;

    @Before
    public void setUp() {
        editBarProfilePageController = new EditBarProfilePageController(userService, barService);

        editBarProfilePageController.addressField = new TextField();
        editBarProfilePageController.nameField = new TextField();
        editBarProfilePageController.emailField = new TextField();
        editBarProfilePageController.userTypeField = new TextField();
        editBarProfilePageController.requiredAdreesErrorLabel = new Label();
        editBarProfilePageController.profilePhoto = new ImageView();
        editBarProfilePageController.scheduleGridController = new ScheduleGridController();
        editBarProfilePageController.scheduleGridController.scheduleGridPane = new GridPane();
        editBarProfilePageController.scheduleGridController.gridVBox = new VBox();
        editBarProfilePageController.scheduleGridController.setLoadStrategy(new ReadonlyScheduleLoadStrategy());
    }

    @Test
    public void testInitializeNullUser() {
        LoggedUserData.getInstance().setUserModel(null);

        editBarProfilePageController.initialize(null, null);

        assertTrue(editBarProfilePageController.nameField.getText().isEmpty());
        assertTrue(editBarProfilePageController.addressField.getText().isEmpty());
        assertTrue(editBarProfilePageController.emailField.getText().isEmpty());
        assertTrue(editBarProfilePageController.userTypeField.getText().isEmpty());
        assertFalse(editBarProfilePageController.requiredAdreesErrorLabel.isVisible());
    }

    @Test
    public void testInitializeIsUserLogged() {
        UserModel userModel = new UserModel(89, "email@yahoo.com", "parola", "Bar Name", UserType.Manager);
        LoggedUserData.getInstance().setUserModel(userModel);

        when(barService.getBar(89)).thenReturn(new BarModel(89, "Sucevei Nr 23"));

        editBarProfilePageController.initialize(null, null);

        assertEquals("Bar Name", editBarProfilePageController.nameField.getText());
        assertEquals("Sucevei Nr 23", editBarProfilePageController.addressField.getText());
        assertEquals("email@yahoo.com", editBarProfilePageController.emailField.getText());
        assertEquals(UserType.Manager.toString(), editBarProfilePageController.userTypeField.getText());
    }

    @Test
    public void testInitializeNullBar() {
        UserModel userModel = new UserModel(89, "email@yahoo.com", "parola", "Bar Name", UserType.Manager);
        LoggedUserData.getInstance().setUserModel(userModel);

        when(barService.getBar(89)).thenReturn(null);

        editBarProfilePageController.initialize(null, null);

        assertEquals("Bar Name", editBarProfilePageController.nameField.getText());
        assertEquals("", editBarProfilePageController.addressField.getText());
        assertEquals("email@yahoo.com", editBarProfilePageController.emailField.getText());
        assertEquals(UserType.Manager.toString(), editBarProfilePageController.userTypeField.getText());
    }

    @Test
    public void testOnSaveChangesButtonClickEmptyAddressField()  {
        UserModel userModel = new UserModel(89, "email@yahoo.com", "parola", "Bar Name", UserType.Manager);
        LoggedUserData.getInstance().setUserModel(userModel);

        when(barService.getBar(89)).thenReturn(new BarModel(89, ""));

        editBarProfilePageController.initialize(null, null);
        editBarProfilePageController.onSaveChangesButtonClick(null);
        assertTrue(editBarProfilePageController.requiredAdreesErrorLabel.isVisible());
    }

    @Test
    public void testOnSaveChangesButtonClickValidAddressField()  {
        UserModel userModel = new UserModel(89, "email@yahoo.com", "parola", "Bar Name", UserType.Manager);
        LoggedUserData.getInstance().setUserModel(userModel);

        when(barService.getBar(89)).thenReturn(new BarModel(89, "Sucevei Nr 23"));

        editBarProfilePageController.initialize(null, null);

        editBarProfilePageController.onSaveChangesButtonClick(null);
        assertFalse(editBarProfilePageController.requiredAdreesErrorLabel.isVisible());
    }
}