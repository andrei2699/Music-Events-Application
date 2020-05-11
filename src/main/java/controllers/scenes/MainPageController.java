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

public class MainPageController implements Initializable {

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

    @FXML
    public ImageView moreActionsImage;


    private FilteredList<TableCardModel> eventModelFilteredList;
    private FilteredList<TableCardModel> barModelFilteredList;
    private FilteredList<TableCardModel> artistModelFilteredList;

    private ContextMenu moreActionsContextMenu;

    private EventService eventService;
    private BarService barService;
    private ArtistService artistService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventService = ServiceProvider.getEventService();
        barService = ServiceProvider.getBarService();
        artistService = ServiceProvider.getArtistService();

        moreActionsContextMenu = new ContextMenu();

        if (LoggedUserData.getInstance().isBarManager() || LoggedUserData.getInstance().isArtist()) {
            MenuItem goToProfileMenuItem = new CheckMenuItem("Vezi profil");
            goToProfileMenuItem.setOnAction(this::goViewProfile);
            moreActionsContextMenu.getItems().add(goToProfileMenuItem);
        }

        if (LoggedUserData.getInstance().isBarManager()) {
            MenuItem goToCreateEventForm = new CheckMenuItem("Creaza eveniment");
            goToCreateEventForm.setOnAction(event -> SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.CreateEventFormScene));
            moreActionsContextMenu.getItems().add(goToCreateEventForm);
        }

        MenuItem goToLogin;
        if (LoggedUserData.getInstance().isUserLogged()) {
            goToLogin = new CheckMenuItem("Delogare");
        } else {
            goToLogin = new CheckMenuItem("Logare");
        }

        goToLogin.setOnAction(event -> SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.LoginScene));

        moreActionsContextMenu.getItems().add(goToLogin);

        // events table view
        eventModelFilteredList = new FilteredList<>(getAllEvents(), m -> true);
        CardTableFiller.setupTable(eventsSearchTextField, eventModelFilteredList, eventsTableView, eventsTableColumn, DetailsTableConfigData.getEventTableColumnData());


        // bars table view
        barModelFilteredList = new FilteredList<>(getAllBars(), m -> true);
        CardTableFiller.setupTable(barSearchTextField, barModelFilteredList, barsTableView, barsTableColumn, DetailsTableConfigData.getBarTableColumnData());


        // artists table view
        artistModelFilteredList = new FilteredList<>(getAllArtists(), m -> true);
        CardTableFiller.setupTable(artistSearchTextField, artistModelFilteredList, artistsTableView, artistsTableColumn, DetailsTableConfigData.getArtistTableColumnData());
    }

    @FXML
    public void onSearchImageClicked(MouseEvent mouseEvent) {
        eventsSearchTextField.requestFocus();
    }

    @FXML
    public void onMoreActionsClicked(MouseEvent mouseEvent) {
        Bounds bounds = moreActionsImage.localToScreen(moreActionsImage.getBoundsInLocal());
        double x = bounds.getMaxX();
        double y = bounds.getMaxY();
        moreActionsContextMenu.show(moreActionsImage, x, y);
        moreActionsContextMenu.setX(x - moreActionsContextMenu.getWidth());
    }

    public void goViewProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewArtistProfileScene, LoggedUserData.getInstance().getUserModel().getId());
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewBarProfileScene, LoggedUserData.getInstance().getUserModel().getId());
        }
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
