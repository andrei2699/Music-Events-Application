package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.scene.control.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MakeReservationPopupWindowControllerTest extends ApplicationTest {

    private MakeReservationPopupWindowController makeReservationPopupWindowController;

    private static final int MAXIMUM_SEAT_NUMBER = 900;
    private static final int SELECTED_SEAT_NUMBER = 4;

    @Before
    public void setUp() {
        makeReservationPopupWindowController = new MakeReservationPopupWindowController();
        makeReservationPopupWindowController.selectedSeatsSlider = new Slider();
        makeReservationPopupWindowController.ticketPriceLabel = new Label();
        makeReservationPopupWindowController.totalSeatsLabel = new Label();
        makeReservationPopupWindowController.selectedTextField = new PasswordField();
        makeReservationPopupWindowController.reserveButton = new Button();
        makeReservationPopupWindowController.cancelButton = new Button();
        makeReservationPopupWindowController.cvcTextField = new TextField();
        makeReservationPopupWindowController.cardNumberTextField = new TextField();
        makeReservationPopupWindowController.expirationDateDatePicker = new DatePicker();
    }

    @Test
    public void testInitialize() {
        makeReservationPopupWindowController.setMaximumNumberOfSeats(MAXIMUM_SEAT_NUMBER);
        makeReservationPopupWindowController.setPopupResponseCall(null);
        makeReservationPopupWindowController.initialize(null, null);

        assertEquals(MAXIMUM_SEAT_NUMBER, (int) makeReservationPopupWindowController.selectedSeatsSlider.getMax());
        assertEquals("1", makeReservationPopupWindowController.selectedTextField.getText());

        makeReservationPopupWindowController.selectedSeatsSlider.setValue(SELECTED_SEAT_NUMBER);
        assertEquals(String.valueOf(SELECTED_SEAT_NUMBER), makeReservationPopupWindowController.selectedTextField.getText());

        makeReservationPopupWindowController.selectedTextField.setText(String.valueOf(SELECTED_SEAT_NUMBER));
        assertEquals(SELECTED_SEAT_NUMBER, (int) makeReservationPopupWindowController.selectedSeatsSlider.getValue());

        makeReservationPopupWindowController.selectedTextField.setText(String.valueOf(MAXIMUM_SEAT_NUMBER + 2));
        assertEquals(MAXIMUM_SEAT_NUMBER, (int) makeReservationPopupWindowController.selectedSeatsSlider.getValue());

        makeReservationPopupWindowController.selectedTextField.setText("0");
        assertEquals(1, (int) makeReservationPopupWindowController.selectedSeatsSlider.getValue());

        int expected_value = 5;
        makeReservationPopupWindowController.selectedSeatsSlider.setValue(expected_value);
        makeReservationPopupWindowController.selectedTextField.setText("test");
        assertEquals(expected_value, (int) makeReservationPopupWindowController.selectedSeatsSlider.getValue());
    }

    @Test
    public void testOnReserveButtonClick() {
        makeReservationPopupWindowController.setMaximumNumberOfSeats(MAXIMUM_SEAT_NUMBER);
        makeReservationPopupWindowController.setTicketPrice(10);
        makeReservationPopupWindowController.cvcTextField.setText("123");
        makeReservationPopupWindowController.cardNumberTextField.setText("1234565686561245");
        makeReservationPopupWindowController.expirationDateDatePicker.setValue(LocalDate.now());
        AtomicReference<Boolean> methodCalled = new AtomicReference<>(false);
        ISceneResponseCall<Integer> responseCall = callResult -> methodCalled.set(true);

        makeReservationPopupWindowController.setPopupResponseCall(responseCall);
        makeReservationPopupWindowController.initialize(null, null);
        makeReservationPopupWindowController.reserveButton.fire();

        assertTrue(methodCalled.get());
    }
}