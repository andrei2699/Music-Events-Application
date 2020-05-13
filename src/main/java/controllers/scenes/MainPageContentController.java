package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import models.cards.TableCardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.*;
import models.cards.ArtistCardModel;
import models.cards.BarCardModel;
import models.cards.EventCardModel;
import models.other.UserType;
import services.ArtistService;
import services.BarService;
import services.EventService;
import services.ServiceProvider;
import utils.CardTableFiller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageContentController implements Initializable {

    @FXML
    public TableView<TableCardModel> eventsTableView;
    @FXML
    public TableColumn<TableCardModel, TableCardModel> eventsTableColumn;
    @FXML
    public TextField eventsSearchTextField;

    @FXML
    public TextField barSearchTextField;
    @FXML
    public TableView<TableCardModel> artistsTableView;
    @FXML
    public TableColumn<TableCardModel, TableCardModel> artistsTableColumn;

    @FXML
    public TextField artistSearchTextField;
    @FXML
    public TableView<TableCardModel> barsTableView;
    @FXML
    public TableColumn<TableCardModel, TableCardModel> barsTableColumn;

    private EventService eventService;
    private BarService barService;
    private ArtistService artistService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService = ServiceProvider.getEventService();
        barService = ServiceProvider.getBarService();
        artistService = ServiceProvider.getArtistService();

        // events table view
        FilteredList<TableCardModel> eventModelFilteredList = new FilteredList<>(getAllEvents(), m -> true);
        CardTableFiller.setupTable(eventsSearchTextField, eventModelFilteredList, eventsTableView, eventsTableColumn, DetailsTableConfigData.getEventTableColumnData());


        // bars table view
        FilteredList<TableCardModel> barModelFilteredList = new FilteredList<>(getAllBars(), m -> true);
        CardTableFiller.setupTable(barSearchTextField, barModelFilteredList, barsTableView, barsTableColumn, DetailsTableConfigData.getBarTableColumnData());


        // artists table view
        FilteredList<TableCardModel> artistModelFilteredList = new FilteredList<>(getAllArtists(), m -> true);
        CardTableFiller.setupTable(artistSearchTextField, artistModelFilteredList, artistsTableView, artistsTableColumn, DetailsTableConfigData.getArtistTableColumnData());
    }

    @FXML
    public void onSearchEventImageClicked(MouseEvent mouseEvent) {
        eventsSearchTextField.requestFocus();
    }

    @FXML
    public void onSearchBarImageClicked(MouseEvent mouseEvent) {
        barSearchTextField.requestFocus();
    }

    @FXML
    public void onSearchArtistImageClicked(MouseEvent mouseEvent) {
        artistSearchTextField.requestFocus();
    }

    private ObservableList<TableCardModel> getAllEvents() {
        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();

        List<EventModel> allEvents = eventService.getAllEvents();
        for (EventModel eventModel : allEvents) {
            eventModels.add(new EventCardModel(eventModel));
        }

        return eventModels;
    }

    private ObservableList<TableCardModel> getAllBars() {
        ObservableList<TableCardModel> barModels = FXCollections.observableArrayList();

        List<BarModel> allEvents = barService.getAllBars();
        for (BarModel barModel : allEvents) {
            barModels.add(new BarCardModel(barModel));
        }

        return barModels;
    }

    private ObservableList<TableCardModel> getAllArtists() {
        ObservableList<TableCardModel> artistModels = FXCollections.observableArrayList();

        List<ArtistModel> allEvents = artistService.getAllArtists();
        for (ArtistModel artistModel : allEvents) {
            artistModels.add(new ArtistCardModel(artistModel));
        }

        return artistModels;
    }
}
