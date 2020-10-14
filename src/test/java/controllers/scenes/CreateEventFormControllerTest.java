package controllers.scenes;

import javafx.scene.control.*;
import main.LoggedUserData;
import models.EventModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IEventService;
import services.IUserService;

import java.time.LocalDate;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateEventFormControllerTest extends ApplicationTest {
    private CreateEventFormController createEventFormController;

    @Mock
    private IUserService userService;
    @Mock
    private IEventService eventService;

    @Before
    public void setUp() {
        createEventFormController = new CreateEventFormController(userService, eventService);

        createEventFormController.eventNameField = new TextField();
        createEventFormController.barNameField = new TextField();
        createEventFormController.artistNameField = new TextField();
        createEventFormController.seatNumberField = new TextField();
        createEventFormController.descriptionField = new TextArea();
        createEventFormController.datePicker = new DatePicker();
        createEventFormController.eventNameErrorLabel = new Label();
        createEventFormController.artistNameErrorLabel = new Label();
        createEventFormController.startHourErrorLabel = new Label();
        createEventFormController.seatNumberErrorLabel = new Label();
        createEventFormController.dateErrorLabel = new Label();
        createEventFormController.titleLabel = new Label();
        createEventFormController.startHourComboBox = new ComboBox<>();
        createEventFormController.ticketPriceTextField = new TextField();
        createEventFormController.ticketPriceErrorLabel = new Label();
    }

    @Test
    public void testInitialize() {
        LoggedUserData.getInstance().setUserModel(new UserModel(12, "email@yahoo.com", "Pass", "Name", UserType.RegularUser));

        createEventFormController.initialize(null, null);

        assertFalse(createEventFormController.startHourErrorLabel.isVisible());
        assertFalse(createEventFormController.seatNumberErrorLabel.isVisible());
        assertFalse(createEventFormController.artistNameErrorLabel.isVisible());
        assertFalse(createEventFormController.dateErrorLabel.isVisible());
        assertFalse(createEventFormController.eventNameErrorLabel.isVisible());

        assertEquals(24, createEventFormController.startHourComboBox.getItems().size());
        for (int i = 0; i < 24; i++) {
            assertEquals(i, createEventFormController.startHourComboBox.getItems().get(i).intValue());
        }

        createEventFormController.seatNumberField.setText("12smth");
        assertEquals("12", createEventFormController.seatNumberField.getText());

        createEventFormController.seatNumberField.setText("17");
        assertEquals("17", createEventFormController.seatNumberField.getText());

        createEventFormController.seatNumberField.setText("miau10ura");
        assertEquals("10", createEventFormController.seatNumberField.getText());

        createEventFormController.seatNumberField.setText("some");
        assertEquals("", createEventFormController.seatNumberField.getText());

        createEventFormController.seatNumberField.setText("");
        assertEquals("", createEventFormController.seatNumberField.getText());

        createEventFormController.seatNumberField.setText(".]'!:1;.,.");
        assertEquals("1", createEventFormController.seatNumberField.getText());

        assertEquals("Name", createEventFormController.barNameField.getText());
    }

    @Test
    public void testInitializeWithNull() {
        LoggedUserData.getInstance().setUserModel(null);

        createEventFormController.initialize(null, null);

        assertTrue(createEventFormController.barNameField.getText().isEmpty());
    }

    @Test
    public void testOnSetModelIdValid() {
        EventModel eventModel = new EventModel(56, 67, 78, "Event Name", 10, "2020-10-10", 12, 200, "Description");
        UserModel artistModel = new UserModel(78, "artist@yahoo.com", "pass", "Artist Name", UserType.Artist);

        when(eventService.getEventUsingEventId(56)).thenReturn(eventModel);
        when(userService.getUser(78)).thenReturn(artistModel);

        createEventFormController.onSetModelId(56);

        assertEquals(EDIT_EVENT_FORM_TEXT, createEventFormController.titleLabel.getText());
        assertEquals(eventModel.getName(), createEventFormController.eventNameField.getText());
        assertEquals(eventModel.getStart_hour(), createEventFormController.startHourComboBox.getValue().intValue());
        assertEquals(eventModel.getDate(), createEventFormController.datePicker.getValue().toString());
        assertEquals(eventModel.getTotal_seats() + "", createEventFormController.seatNumberField.getText());
        assertEquals(eventModel.getDescription(), createEventFormController.descriptionField.getText());

        assertTrue(createEventFormController.barNameField.isDisabled());
        assertTrue(createEventFormController.artistNameField.isDisabled());
    }

    @Test
    public void testOnSetModelIdNull() {
        when(eventService.getEventUsingEventId(56)).thenReturn(null);

        createEventFormController.onSetModelId(56);

        assertEquals(EDIT_EVENT_FORM_TEXT, createEventFormController.titleLabel.getText());
        assertTrue(createEventFormController.eventNameField.getText().isEmpty());
        assertNull(createEventFormController.startHourComboBox.getValue());
        assertNull(createEventFormController.datePicker.getValue());
        assertTrue(createEventFormController.seatNumberField.getText().isEmpty());
        assertTrue(createEventFormController.descriptionField.getText().isEmpty());
    }

    @Test
    public void testOnSaveDetailsButtonClickWithEmptyFields() {
        createEventFormController.eventNameField.setText("   ");
        createEventFormController.datePicker.setValue(null);
        createEventFormController.seatNumberField.setText("");
        createEventFormController.startHourComboBox.setValue(null);
        createEventFormController.artistNameField.setText("   ");

        createEventFormController.onSaveDetailsButtonClick(null);

        assertTrue(createEventFormController.eventNameErrorLabel.isVisible());
        assertTrue(createEventFormController.dateErrorLabel.isVisible());
        assertTrue(createEventFormController.startHourErrorLabel.isVisible());
        assertTrue(createEventFormController.seatNumberErrorLabel.isVisible());
        assertEquals(REQUIRED_FIELD_ERROR_MESSAGE, createEventFormController.artistNameErrorLabel.getText());
    }

    @Test
    public void testOnSaveDetailsButtonClickWithNullArtist() {
        createEventFormController.eventNameField.setText("Event Name");
        createEventFormController.datePicker.setValue(LocalDate.of(2020, 10, 10));
        createEventFormController.seatNumberField.setText("");
        createEventFormController.startHourComboBox.setValue(null);
        createEventFormController.artistNameField.setText("Artist Name");

        when(userService.getArtist("Artist Name")).thenReturn(null);

        createEventFormController.onSaveDetailsButtonClick(null);

        assertFalse(createEventFormController.eventNameErrorLabel.isVisible());
        assertFalse(createEventFormController.dateErrorLabel.isVisible());
        assertTrue(createEventFormController.startHourErrorLabel.isVisible());
        assertTrue(createEventFormController.seatNumberErrorLabel.isVisible());
        assertEquals(INVALID_ARTIST_NAME_ERROR_MESSAGE, createEventFormController.artistNameErrorLabel.getText());
    }

    @Test
    public void testOnSaveDetailsButtonClickWithValidData() {
        createEventFormController.eventNameField.setText("Event Name");
        createEventFormController.datePicker.setValue(LocalDate.of(2020, 10, 10));
        createEventFormController.seatNumberField.setText("120");
        createEventFormController.startHourComboBox.setValue(14);
        createEventFormController.artistNameField.setText("Artist Name");

        UserModel artistModel = new UserModel(78, "artist@yahoo.com", "pass", "Artist Name", UserType.Artist);
        when(userService.getArtist("Artist Name")).thenReturn(artistModel);

        EventModel eventModel = new EventModel(12, 67, 78, "Event Name", 20, "2020-10-10", 12, 200, "Description");

        when(eventService.getEventUsingEventId(12)).thenReturn(eventModel);
        when(userService.getUser(78)).thenReturn(artistModel);

        try {
            createEventFormController.onSetModelId(12);
            createEventFormController.onSaveDetailsButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        assertFalse(createEventFormController.eventNameErrorLabel.isVisible());
        assertFalse(createEventFormController.dateErrorLabel.isVisible());
        assertFalse(createEventFormController.startHourErrorLabel.isVisible());
        assertFalse(createEventFormController.seatNumberErrorLabel.isVisible());
        assertFalse(createEventFormController.artistNameErrorLabel.isVisible());
    }

    @Test
    public void testOnSaveDetailsButtonClickWithValidDataNullEvent() {
        createEventFormController.eventNameField.setText("Event Name");
        createEventFormController.datePicker.setValue(LocalDate.of(2020, 10, 10));
        createEventFormController.seatNumberField.setText("120");
        createEventFormController.startHourComboBox.setValue(14);
        createEventFormController.artistNameField.setText("Artist Name");

        UserModel artistModel = new UserModel(78, "artist@yahoo.com", "pass", "Artist Name", UserType.Artist);
        when(userService.getArtist("Artist Name")).thenReturn(artistModel);

        LoggedUserData.getInstance().setUserModel(new UserModel(67, "email@yahoo.com", "password", "Name", UserType.Manager));
        try {
            createEventFormController.onSaveDetailsButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        assertFalse(createEventFormController.eventNameErrorLabel.isVisible());
        assertFalse(createEventFormController.dateErrorLabel.isVisible());
        assertFalse(createEventFormController.startHourErrorLabel.isVisible());
        assertFalse(createEventFormController.seatNumberErrorLabel.isVisible());
        assertFalse(createEventFormController.artistNameErrorLabel.isVisible());
    }
}