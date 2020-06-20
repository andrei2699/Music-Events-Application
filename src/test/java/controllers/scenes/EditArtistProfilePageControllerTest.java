package controllers.scenes;

import controllers.components.VideoPlayerComponentController;
import controllers.components.scheduleGrid.ReadonlyScheduleLoadStrategy;
import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.ArtistModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IArtistService;
import services.IUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EditArtistProfilePageControllerTest extends ApplicationTest {
    private EditArtistProfilePageController editArtistProfilePageController;

    @Mock
    private IUserService userService;
    @Mock
    private IArtistService artistService;

    @Before
    public void setUp() {
        editArtistProfilePageController = new EditArtistProfilePageController(userService, artistService);

        editArtistProfilePageController.genreField = new TextField();
        editArtistProfilePageController.bandMembersField = new TextArea();
        editArtistProfilePageController.nameField = new TextField();
        editArtistProfilePageController.emailField = new TextField();
        editArtistProfilePageController.userTypeField = new TextField();
        editArtistProfilePageController.bandMembersLabel = new Label();
        editArtistProfilePageController.bandCheckBox = new CheckBox();
        editArtistProfilePageController.videoPlayerComponentController = new VideoPlayerComponentController();
        editArtistProfilePageController.profilePhoto = new ImageView();
        editArtistProfilePageController.scheduleGridController = new ScheduleGridController();
        editArtistProfilePageController.scheduleGridController.scheduleGridPane = new GridPane();
        editArtistProfilePageController.scheduleGridController.gridVBox = new VBox();
        editArtistProfilePageController.scheduleGridController.setLoadStrategy(new ReadonlyScheduleLoadStrategy());
    }

    @Test
    public void testInitializeNullUser() {
        LoggedUserData.getInstance().setUserModel(null);

        editArtistProfilePageController.initialize(null, null);

        assertTrue(editArtistProfilePageController.nameField.getText().isEmpty());
        assertTrue(editArtistProfilePageController.emailField.getText().isEmpty());
        assertTrue(editArtistProfilePageController.userTypeField.getText().isEmpty());
        assertTrue(editArtistProfilePageController.bandMembersField.getText().isEmpty());
        assertTrue(editArtistProfilePageController.genreField.getText().isEmpty());
        assertFalse(editArtistProfilePageController.bandCheckBox.isSelected());
    }

    @Test
    public void testInitializeIsUserLoggedBand() {
        UserModel userModel = new UserModel(22, "email@yahoo.com", "parola", "Band Name", UserType.Artist);
        LoggedUserData.getInstance().setUserModel(userModel);

        ArtistModel artistModel = new ArtistModel(22, true, "progressive rock");
        artistModel.setMembers("Andrei, Tania, Paul, Gabi");
        when(artistService.getArtist(22)).thenReturn(artistModel);

        editArtistProfilePageController.initialize(null, null);

        assertEquals("Band Name", editArtistProfilePageController.nameField.getText());
        assertEquals("progressive rock", editArtistProfilePageController.genreField.getText());
        assertEquals("Andrei, Tania, Paul, Gabi", editArtistProfilePageController.bandMembersField.getText());
        assertEquals("email@yahoo.com", editArtistProfilePageController.emailField.getText());
        assertEquals(UserType.Artist.toString(), editArtistProfilePageController.userTypeField.getText());
        assertTrue(editArtistProfilePageController.bandCheckBox.isSelected());
    }

    @Test
    public void testInitializeIsUserLoggedSoloArtist() {
        UserModel userModel = new UserModel(22, "artist@yahoo.com", "pasw", "Artist Name", UserType.Artist);
        LoggedUserData.getInstance().setUserModel(userModel);

        ArtistModel artistModel = new ArtistModel(22, false, "alternative rock");
        when(artistService.getArtist(22)).thenReturn(artistModel);

        editArtistProfilePageController.initialize(null, null);

        assertEquals("Artist Name", editArtistProfilePageController.nameField.getText());
        assertEquals("alternative rock", editArtistProfilePageController.genreField.getText());
        assertEquals("", editArtistProfilePageController.bandMembersField.getText());
        assertEquals("artist@yahoo.com", editArtistProfilePageController.emailField.getText());
        assertEquals(UserType.Artist.toString(), editArtistProfilePageController.userTypeField.getText());
        assertFalse(editArtistProfilePageController.bandCheckBox.isSelected());
    }

    @Test
    public void testInitializeNullArtist() {
        UserModel userModel = new UserModel(22, "email@yahoo.com", "parola", "Artist Name", UserType.Artist);
        LoggedUserData.getInstance().setUserModel(userModel);

        when(artistService.getArtist(22)).thenReturn(null);

        editArtistProfilePageController.initialize(null, null);

        assertEquals("Artist Name", editArtistProfilePageController.nameField.getText());
        assertEquals("", editArtistProfilePageController.genreField.getText());
        assertEquals("", editArtistProfilePageController.bandMembersField.getText());
        assertEquals("email@yahoo.com", editArtistProfilePageController.emailField.getText());
        assertEquals(UserType.Artist.toString(), editArtistProfilePageController.userTypeField.getText());
        assertFalse(editArtistProfilePageController.bandCheckBox.isSelected());
    }

    @Test
    public void testOnSelectBandCheckBoxClick() {
        editArtistProfilePageController.bandCheckBox.setSelected(true);
        editArtistProfilePageController.onSelectBandCheckBoxClick(null);
        assertTrue(editArtistProfilePageController.bandMembersField.isVisible());
        assertTrue(editArtistProfilePageController.bandMembersLabel.isVisible());

        editArtistProfilePageController.bandCheckBox.setSelected(false);
        editArtistProfilePageController.onSelectBandCheckBoxClick(null);
        assertFalse(editArtistProfilePageController.bandMembersField.isVisible());
        assertFalse(editArtistProfilePageController.bandMembersLabel.isVisible());
    }

    @Test
    public void testOnSaveChangesButtonClickNullArtist() {
        LoggedUserData.getInstance().setUserModel(null);
        try {
            editArtistProfilePageController.onSaveChangesButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testOnSaveChangesButtonClickNOTNull() {
        UserModel userModel = new UserModel(22, "email@yahoo.com", "parola", "Band Name", UserType.Artist);
        LoggedUserData.getInstance().setUserModel(userModel);

        ArtistModel artistModel = new ArtistModel(22, true, "progressive rock");
        artistModel.setMembers("Andrei, Tania, Paul, Gabi");
        when(artistService.getArtist(22)).thenReturn(artistModel);

        editArtistProfilePageController.initialize(null, null);

        try {
            editArtistProfilePageController.onSaveChangesButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }
    }
}