package controllers.components.cardsTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import models.cards.*;
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
public class CardsTableViewControllerTest extends ApplicationTest {
    @Mock
    private DetailsTableConfigData detailsTableConfigData;
    @Mock
    private IUserService userService;

    private CardsTableViewController cardsTableViewController;

    private static final String NO_DATA = "Fara Date";
    private static final String CELL_FACTORY_PROPERTY_VALUE = "eventCardModel";
    private static final String COLUMN_TEXT = "COLUMN TEXT";

    @Before
    public void setUp() {
        cardsTableViewController = new CardsTableViewController();
        cardsTableViewController.cardsTableView = new TableView<>();
        cardsTableViewController.cardsTableColumn = new TableColumn<>();
    }

    @Test
    public void testSetItems() {
        when(userService.getUser(4)).thenReturn(new UserModel(4, "bar@yahoo.com", "psw", "Bar", UserType.Manager));
        when(userService.getUser(7)).thenReturn(new UserModel(7, "bar2@yahoo.com", "psfwew", "Bar2", UserType.Manager));
        when(userService.getUser(6)).thenReturn(new UserModel(6, "artist@yahoo.com", "psvxccw", "Artist", UserType.Artist));
        when(userService.getUser(5)).thenReturn(new UserModel(5, "artist2@yahoo.com", "psdasdccw", "Artist2", UserType.Artist));

        DiscussionHeaderCardModel discussionHeaderCardModel = new DiscussionHeaderCardModel(new DiscussionModel(1, 2, 3), userService);
        EventCardModel eventCardModel = new EventCardModel(new EventModel(2, 4, 6, "Name", "06-07-2020", 5, 120), userService);
        ArtistCardModel artistCardModel = new ArtistCardModel(new ArtistModel(5, false, "Folk"), userService);
        BarCardModel barCardModel = new BarCardModel(new BarModel(7, "My address"), userService);

        ObservableList<TableCardModel> models = FXCollections.observableArrayList();
        models.add(discussionHeaderCardModel);
        models.add(eventCardModel);
        models.add(artistCardModel);
        models.add(barCardModel);

        cardsTableViewController.setItems(models);

        assertEquals(models.size(), cardsTableViewController.cardsTableView.getItems().size());
        assertEquals(models, cardsTableViewController.cardsTableView.getItems());
    }

    @Test
    public void testSetColumnData() {
        when(detailsTableConfigData.getNoContentLabelText()).thenReturn(NO_DATA);
        when(detailsTableConfigData.getPropertyValueFactory()).thenReturn(CELL_FACTORY_PROPERTY_VALUE);
        when(detailsTableConfigData.getTableColumnText()).thenReturn(COLUMN_TEXT);

        cardsTableViewController.setColumnData(detailsTableConfigData);

        assertEquals(NO_DATA, ((Label) cardsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals(CELL_FACTORY_PROPERTY_VALUE, ((PropertyValueFactory<TableCardModel, TableCardModel>) cardsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(COLUMN_TEXT, cardsTableViewController.cardsTableColumn.getText());
    }

    @Test
    public void testGetItem() {
        when(userService.getUser(4)).thenReturn(new UserModel(4, "bar@yahoo.com", "psw", "Bar", UserType.Manager));
        when(userService.getUser(7)).thenReturn(new UserModel(7, "bar2@yahoo.com", "psfwew", "Bar2", UserType.Manager));
        when(userService.getUser(6)).thenReturn(new UserModel(6, "artist@yahoo.com", "psvxccw", "Artist", UserType.Artist));
        when(userService.getUser(5)).thenReturn(new UserModel(5, "artist2@yahoo.com", "psdasdccw", "Artist2", UserType.Artist));

        DiscussionHeaderCardModel discussionHeaderCardModel = new DiscussionHeaderCardModel(new DiscussionModel(1, 2, 3), userService);
        EventCardModel eventCardModel = new EventCardModel(new EventModel(2, 4, 6, "Name", "06-07-2020", 5, 120), userService);
        ArtistCardModel artistCardModel = new ArtistCardModel(new ArtistModel(5, false, "Folk"), userService);
        BarCardModel barCardModel = new BarCardModel(new BarModel(7, "My address"), userService);

        ObservableList<TableCardModel> models = FXCollections.observableArrayList();
        models.add(discussionHeaderCardModel);
        models.add(eventCardModel);
        models.add(artistCardModel);
        models.add(barCardModel);

        cardsTableViewController.setItems(models);

        assertEquals(discussionHeaderCardModel, cardsTableViewController.getItem(0));
        assertEquals(eventCardModel, cardsTableViewController.getItem(1));
        assertEquals(artistCardModel, cardsTableViewController.getItem(2));
        assertEquals(barCardModel, cardsTableViewController.getItem(3));

        assertNull(cardsTableViewController.getItem(-2));
        assertNull(cardsTableViewController.getItem(100));
    }
}