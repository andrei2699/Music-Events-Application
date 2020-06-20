package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

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
        makeReservationPopupWindowController.selectedTextField = new PasswordField();
        makeReservationPopupWindowController.reserveButton = new Button();
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
        AtomicReference<Boolean> methodCalled = new AtomicReference<>(false);
        ISceneResponseCall<Integer> responseCall = callResult -> methodCalled.set(true);

        makeReservationPopupWindowController.setPopupResponseCall(responseCall);
        makeReservationPopupWindowController.initialize(null, null);
        makeReservationPopupWindowController.reserveButton.fire();

        assertTrue(methodCalled.get());
    }
}