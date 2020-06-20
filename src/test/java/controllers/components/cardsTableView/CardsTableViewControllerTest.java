package controllers.components.cardsTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ArtistModel;
import models.BarModel;
import models.DiscussionModel;
import models.EventModel;
import models.cards.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardsTableViewControllerTest extends ApplicationTest {
    @Mock
    private DetailsTableConfigData detailsTableConfigData;

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
        DiscussionHeaderCardModel discussionHeaderCardModel = new DiscussionHeaderCardModel(new DiscussionModel(1, 2, 3));
        EventCardModel eventCardModel = new EventCardModel(new EventModel(2, 4, 6, "Name", "06-07-2020", 5, 120));
        ArtistCardModel artistCardModel = new ArtistCardModel(new ArtistModel(5, false, "Folk"));
        BarCardModel barCardModel = new BarCardModel(new BarModel(7, "My address"));

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
    public void getItem() {
        DiscussionHeaderCardModel discussionHeaderCardModel = new DiscussionHeaderCardModel(new DiscussionModel(1, 2, 3));
        EventCardModel eventCardModel = new EventCardModel(new EventModel(2, 4, 6, "Name", "06-07-2020", 5, 120));
        ArtistCardModel artistCardModel = new ArtistCardModel(new ArtistModel(5, false, "Folk"));
        BarCardModel barCardModel = new BarCardModel(new BarModel(7, "My address"));

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