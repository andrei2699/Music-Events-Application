package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewWithSearchbarController;
import controllers.components.cardsTableView.DetailsTableConfigData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.ArtistModel;
import models.BarModel;
import models.EventModel;
import models.cards.ArtistCardModel;
import models.cards.BarCardModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.*;

public class MainPageContentController implements Initializable {

    @FXML
    public CardsTableViewWithSearchbarController eventsTableViewWithSearchbarController;
    @FXML
    public CardsTableViewWithSearchbarController barsTableViewWithSearchbarController;
    @FXML
    public CardsTableViewWithSearchbarController artistsTableViewWithSearchbarController;

    private IEventService eventService;
    private IBarService barService;
    private IArtistService artistService;
    private IUserService userService;

    public MainPageContentController() {
        this(ServiceProvider.getUserService(), ServiceProvider.getEventService(), ServiceProvider.getBarService(), ServiceProvider.getArtistService());
    }

    protected MainPageContentController(IUserService userService, IEventService eventService, IBarService barService, IArtistService artistService) {
        this.userService = userService;
        this.eventService = eventService;
        this.barService = barService;
        this.artistService = artistService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // events table view
        FilteredList<TableCardModel> eventModelFilteredList = new FilteredList<>(getAllEvents(), m -> true);
        eventsTableViewWithSearchbarController.setupTableData(SEARCH_FOR_EVENTS_TEXT, eventModelFilteredList, DetailsTableConfigData.getEventTableColumnData(id -> {
            eventModelFilteredList.getSource().removeIf(eventCard -> ((EventCardModel) eventCard).getEventModel().getId() == id);
//            eventModelFilteredList.removeIf(eventCard -> ((EventCardModel) eventCard).getEventModel().getId() == id);
        }));

        // bars table view
        FilteredList<TableCardModel> barModelFilteredList = new FilteredList<>(getAllBars(), m -> true);
        barsTableViewWithSearchbarController.setupTableData(SEARCH_FOR_BARS_TEXT, barModelFilteredList, DetailsTableConfigData.getBarTableColumnData());

        // artists table view
        FilteredList<TableCardModel> artistModelFilteredList = new FilteredList<>(getAllArtists(), m -> true);
        artistsTableViewWithSearchbarController.setupTableData(SEARCH_FOR_ARTISTS_TEXT, artistModelFilteredList, DetailsTableConfigData.getArtistTableColumnData());
    }

    private ObservableList<TableCardModel> getAllEvents() {
        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();
        List<EventModel> allEvents = eventService.getEventsStartingFrom(LocalDate.now(), LocalTime.now().getHour());
        for (EventModel eventModel : allEvents) {
            eventModels.add(new EventCardModel(eventModel, userService));
        }

        return eventModels;
    }

    private ObservableList<TableCardModel> getAllBars() {
        ObservableList<TableCardModel> barModels = FXCollections.observableArrayList();

        List<BarModel> allEvents = barService.getAllBars();
        for (BarModel barModel : allEvents) {
            barModels.add(new BarCardModel(barModel, userService));
        }

        return barModels;
    }

    private ObservableList<TableCardModel> getAllArtists() {
        ObservableList<TableCardModel> artistModels = FXCollections.observableArrayList();

        List<ArtistModel> allEvents = artistService.getAllArtists();
        for (ArtistModel artistModel : allEvents) {
            artistModels.add(new ArtistCardModel(artistModel, userService));
        }

        return artistModels;
    }
}
