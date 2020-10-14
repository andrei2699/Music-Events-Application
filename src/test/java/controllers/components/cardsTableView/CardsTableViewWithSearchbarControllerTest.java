package controllers.components.cardsTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.ArtistModel;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.cards.ArtistCardModel;
import models.cards.EventCardModel;
import models.cards.ReservationCardModel;
import models.cards.TableCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IUserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardsTableViewWithSearchbarControllerTest extends ApplicationTest {
    @Mock
    private DetailsTableConfigData detailsTableConfigData;
    @Mock
    private IUserService userService;

    private CardsTableViewWithSearchbarController cardsTableViewWithSearchbarController;

    private static final String NO_DATA = "NO DATA";
    private static final String CELL_FACTORY_PROPERTY_VALUE = "barCardModel";
    private static final String COLUMN_TEXT = "MY COLUMN TEXT";
    private static final String SEARCH_BAR_PROMPT = "Search things...";

    @Before
    public void setUp() {
        cardsTableViewWithSearchbarController = new CardsTableViewWithSearchbarController();
        cardsTableViewWithSearchbarController.cardsTableViewController = new CardsTableViewController();
        cardsTableViewWithSearchbarController.cardsTableViewController.cardsTableView = new TableView<>();
        cardsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn = new TableColumn<>();
        cardsTableViewWithSearchbarController.searchTextField = new TextField();
    }

    @Test
    public void testSetupTableData() {
        when(detailsTableConfigData.getNoContentLabelText()).thenReturn(NO_DATA);
        when(detailsTableConfigData.getPropertyValueFactory()).thenReturn(CELL_FACTORY_PROPERTY_VALUE);
        when(detailsTableConfigData.getTableColumnText()).thenReturn(COLUMN_TEXT);
        when(userService.getUser(4)).thenReturn(new UserModel(4, "bar3@yahoo.com", "psfwddew", "Bar3", UserType.Manager));
        when(userService.getUser(0)).thenReturn(new UserModel(0, "artist@yahoo.com", "psvxccw", "Artist", UserType.Artist));
        when(userService.getUser(1)).thenReturn(new UserModel(1, "artist3@yahoo.com", "psdccw", "Artist3", UserType.Artist));

        ReservationCardModel reservationCardModel = new ReservationCardModel(new ReservationModel(50, 2, 1, 12));
        EventCardModel eventCardModel = new EventCardModel(new EventModel(1, 4, 0, "Nume", 35,"06-07-2020", 15, 80), userService);
        ArtistCardModel artistCardModel = new ArtistCardModel(new ArtistModel(1, false, "Rock"), userService);

        ObservableList<TableCardModel> models = FXCollections.observableArrayList();
        models.add(reservationCardModel);
        models.add(artistCardModel);
        models.add(eventCardModel);

        FilteredList<TableCardModel> filteredList = new FilteredList<>(models, m -> true);

        cardsTableViewWithSearchbarController.setupTableData(SEARCH_BAR_PROMPT, filteredList, detailsTableConfigData);

        assertEquals(SEARCH_BAR_PROMPT, cardsTableViewWithSearchbarController.searchTextField.getPromptText());

        cardsTableViewWithSearchbarController.searchTextField.setText("Nume");
        assertEquals(eventCardModel, cardsTableViewWithSearchbarController.cardsTableViewController.getItem(0));

        cardsTableViewWithSearchbarController.searchTextField.setText("some Weird search filter");
        assertNull(cardsTableViewWithSearchbarController.cardsTableViewController.getItem(0));

        cardsTableViewWithSearchbarController.searchTextField.setText("");
        assertEquals(reservationCardModel, cardsTableViewWithSearchbarController.cardsTableViewController.getItem(0));
    }
}