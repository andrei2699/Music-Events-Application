package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.cardsTableView.CardsTableViewWithSearchbarController;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ArtistModel;
import models.BarModel;
import models.EventModel;
import models.UserModel;
import models.cards.TableCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IArtistService;
import services.IBarService;
import services.IEventService;
import services.IUserService;

import java.util.ArrayList;
import java.util.List;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPageContentControllerTest extends ApplicationTest {
    private MainPageContentController mainPageContentController;

    @Mock
    private IUserService userService;
    @Mock
    private IEventService eventService;
    @Mock
    private IBarService barService;
    @Mock
    private IArtistService artistService;

    @Before
    public void setUp() {
        mainPageContentController = new MainPageContentController(userService, eventService, barService, artistService);

        mainPageContentController.eventsTableViewWithSearchbarController = new CardsTableViewWithSearchbarController();
        mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController = new CardsTableViewController();
        mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn = new TableColumn<>();
        mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController.cardsTableView = new TableView<>();
        mainPageContentController.eventsTableViewWithSearchbarController.searchTextField = new TextField();

        mainPageContentController.barsTableViewWithSearchbarController = new CardsTableViewWithSearchbarController();
        mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController = new CardsTableViewController();
        mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn = new TableColumn<>();
        mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController.cardsTableView = new TableView<>();
        mainPageContentController.barsTableViewWithSearchbarController.searchTextField = new TextField();

        mainPageContentController.artistsTableViewWithSearchbarController = new CardsTableViewWithSearchbarController();
        mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController = new CardsTableViewController();
        mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn = new TableColumn<>();
        mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController.cardsTableView = new TableView<>();
        mainPageContentController.artistsTableViewWithSearchbarController.searchTextField = new TextField();
    }

    @Test
    public void testInitialize() {
        List<EventModel> eventsList = new ArrayList<>();
        eventsList.add(new EventModel(1, 12, 23, "Event1", "2020-06-05", 17, 130));
        eventsList.add(new EventModel(2, 14, 32, "Event2", "2020-07-10", 10, 40));
        eventsList.add(new EventModel(3, 13, 3, "Event3", "2020-11-12", 20, 100));

        List<BarModel> barsList = new ArrayList<>();
        barsList.add(new BarModel(12, "Albinelor Nr 14"));
        barsList.add(new BarModel(13, "Zmeurei Nr 43"));
        barsList.add(new BarModel(8, "Uranus Nr 12"));
        barsList.add(new BarModel(14, "Venus Nr 32"));

        List<ArtistModel> artistList = new ArrayList<>();
        artistList.add(new ArtistModel(3, false, "pop"));
        artistList.add(new ArtistModel(23, true, "rock"));
        artistList.add(new ArtistModel(45, false, "jazz"));
        artistList.add(new ArtistModel(32, true, "classical"));

        when(barService.getAllBars()).thenReturn(barsList);
        when(artistService.getAllArtists()).thenReturn(artistList);

        when(userService.getUser(12)).thenReturn(new UserModel(12, "email1@yahoo.com", "p1324", "Nume1", UserType.Manager));
        when(userService.getUser(13)).thenReturn(new UserModel(13, "email2@yahoo.com", "p2343", "Nume2", UserType.Manager));
        when(userService.getUser(8)).thenReturn(new UserModel(8, "email3@yahoo.com", "p4312", "Nume3", UserType.Manager));
        when(userService.getUser(14)).thenReturn(new UserModel(14, "email4@yahoo.com", "p13443", "Nume4", UserType.Manager));

        when(userService.getUser(3)).thenReturn(new UserModel(3, "email5@yahoo.com", "p1432", "Nume5", UserType.Artist));
        when(userService.getUser(23)).thenReturn(new UserModel(23, "email6@yahoo.com", "p14534", "Nume6", UserType.Artist));
        when(userService.getUser(45)).thenReturn(new UserModel(45, "email7@yahoo.com", "p14353", "Nume7", UserType.Artist));
        when(userService.getUser(32)).thenReturn(new UserModel(32, "email8@yahoo.com", "p1564", "Nume8", UserType.Artist));

        mainPageContentController.initialize(null, null);

        assertEquals(SEARCH_FOR_EVENTS_TEXT, mainPageContentController.eventsTableViewWithSearchbarController.searchTextField.getPromptText());
        assertEquals(SEARCH_FOR_BARS_TEXT, mainPageContentController.barsTableViewWithSearchbarController.searchTextField.getPromptText());
        assertEquals(SEARCH_FOR_ARTISTS_TEXT, mainPageContentController.artistsTableViewWithSearchbarController.searchTextField.getPromptText());

        assertEquals(NO_EVENTS_TEXT, ((Label) mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals("eventCardModel", ((PropertyValueFactory<TableCardModel, TableCardModel>)
                mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(AVAILABLE_EVENTS_TEXT, mainPageContentController.eventsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getText());

        assertEquals(NO_BARS_TEXT, ((Label) mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals("barCardModel", ((PropertyValueFactory<TableCardModel, TableCardModel>)
                mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(BARS_TEXT, mainPageContentController.barsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getText());

        assertEquals(NO_ARTISTS_TEXT, ((Label) mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals("artistCardModel", ((PropertyValueFactory<TableCardModel, TableCardModel>)
                mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(ARTISTS_TEXT, mainPageContentController.artistsTableViewWithSearchbarController.cardsTableViewController.cardsTableColumn.getText());
    }
}