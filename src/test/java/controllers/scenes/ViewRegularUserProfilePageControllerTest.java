package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewController;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.LoggedUserData;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IEventService;
import services.IReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewRegularUserProfilePageControllerTest extends ApplicationTest {

    private ViewRegularUserProfilePageController viewRegularUserProfilePageController;

    @Mock
    private IReservationService reservationService;
    @Mock
    private IEventService eventService;

    @Before
    public void setUp() {
        viewRegularUserProfilePageController = new ViewRegularUserProfilePageController(reservationService, eventService);
        viewRegularUserProfilePageController.reservationsTableViewController = new CardsTableViewController();
        viewRegularUserProfilePageController.reservationsTableViewController.cardsTableView = new TableView<>();
        viewRegularUserProfilePageController.reservationsTableViewController.cardsTableColumn = new TableColumn<>();
        viewRegularUserProfilePageController.nameLabel = new Label();
    }

    @Test
    public void testInitialize() {
        EventModel eventModel1 = new EventModel(12, 13, 14, "Event1", 35, "2020-10-10", 12, 30);
        EventModel eventModel2 = new EventModel(22, 23, 24, "Event2", 35, "2020-11-11", 18, 120);
        EventModel eventModel3 = new EventModel(32, 33, 34, "Event3", 35, "2020-09-09", 19, 70);
        EventModel eventModel4 = new EventModel(42, 13, 34, "Event4", 35, "2020-07-07", 21, 50);
        EventModel eventModel5 = new EventModel(52, 23, 14, "Event5", 35, "2020-08-08", 16, 200);
        List<EventModel> eventsList = new ArrayList<>();
        eventsList.add(eventModel1);
        eventsList.add(eventModel2);
        eventsList.add(eventModel3);
        eventsList.add(eventModel4);
        eventsList.add(eventModel5);

        ReservationModel reservation1 = new ReservationModel(1, 55, 23, 13);
        ReservationModel reservation2 = new ReservationModel(2, 55, 105, 2);
        ReservationModel reservation3 = new ReservationModel(3, 55, 101, 5);
        List<ReservationModel> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);
        reservationList.add(reservation3);

        when(reservationService.getReservationUsingUserId(55)).thenReturn(reservationList);
        when(eventService.getEventsStartingFrom(LocalDate.now(), LocalTime.now().getHour())).thenReturn(eventsList);

        LoggedUserData.getInstance().setUserModel(null);
        viewRegularUserProfilePageController.initialize(null, null);
        assertTrue(viewRegularUserProfilePageController.nameLabel.getText().isEmpty());

        LoggedUserData.getInstance().setUserModel(new UserModel(55, "userModel@yahoo.com", "Parola", "User Name", UserType.RegularUser));
        viewRegularUserProfilePageController.initialize(null, null);
        assertEquals("User Name", viewRegularUserProfilePageController.nameLabel.getText());

        assertNull(viewRegularUserProfilePageController.reservationsTableViewController.getItem(0));
    }
}