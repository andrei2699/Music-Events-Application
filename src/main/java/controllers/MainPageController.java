package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.*;
import services.ArtistService;
import services.BarService;
import services.EventService;
import services.ServiceProvider;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController extends ChangeableSceneController {

    private static final String NO_EVENTS_TABLE_VIEW_LABEL = "Fara evenimente";
    private static final String NO_BARS_TABLE_VIEW_LABEL = "Fara localuri";
    private static final String NO_ARTISTS_TABLE_VIEW_LABEL = "Fara artisti";

    @FXML
    public TableView<EventModelContainer> eventsTableView;
    @FXML
    public TableColumn<EventModelContainer, EventCardModel> eventsTableColumn;
    @FXML
    public TextField eventsSearchTextField;

    @FXML
    public TableView<BarModelContainer> barsTableView;
    @FXML
    public TableColumn<BarModelContainer, BarCardModel> barsTableColumn;
    @FXML
    public TextField barSearchTextField;

    @FXML
    public TableView<ArtistModelContainer> artistsTableView;
    @FXML
    public TableColumn<ArtistModelContainer, ArtistCardModel> artistsTableColumn;
    @FXML
    public TextField artistSearchTextField;

    @FXML
    public ImageView moreActionsImage;

    private FilteredList<EventModelContainer> eventModelFilteredList;
    private FilteredList<BarModelContainer> barModelFilteredList;
    private FilteredList<ArtistModelContainer> artistModelFilteredList;

    private ContextMenu moreActionsContextMenu;

    private EventService eventService;
    private BarService barService;
    private ArtistService artistService;

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

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
    }

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
        eventsTableView.setPlaceholder(new Label(NO_EVENTS_TABLE_VIEW_LABEL));
        eventsTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventCardModel"));
        eventsTableColumn.setCellFactory(cell -> new EventDetailsCardController());

        eventModelFilteredList = new FilteredList<>(getAllEvents(), m -> true);
        eventsTableView.setItems(eventModelFilteredList);

        eventsSearchTextField.textProperty().addListener(observable -> {
            String filter = eventsSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                eventModelFilteredList.setPredicate(m -> true);
            } else {
                eventModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });

        // bars table view
        barsTableView.setPlaceholder(new Label(NO_BARS_TABLE_VIEW_LABEL));
        barsTableColumn.setCellValueFactory(new PropertyValueFactory<>("barCardModel"));
        barsTableColumn.setCellFactory(cell -> new BarDetailsCardController());

        barModelFilteredList = new FilteredList<>(getAllBars(), m -> true);
        barsTableView.setItems(barModelFilteredList);

        barSearchTextField.textProperty().addListener(observable -> {
            String filter = barSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                barModelFilteredList.setPredicate(m -> true);
            } else {
                barModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });

//         artists table view
        artistsTableView.setPlaceholder(new Label(NO_ARTISTS_TABLE_VIEW_LABEL));
        artistsTableColumn.setCellValueFactory(new PropertyValueFactory<>("artistCardModel"));
        artistsTableColumn.setCellFactory(cell -> new ArtistDetailsCardController());

        artistModelFilteredList = new FilteredList<>(getAllArtists(), m -> true);
        artistsTableView.setItems(artistModelFilteredList);

        artistSearchTextField.textProperty().addListener(observable -> {
            String filter = artistSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                artistModelFilteredList.setPredicate(m -> true);
            } else {
                artistModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });
    }

    public void goViewProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewArtistProfileScene,LoggedUserData.getInstance().getUserModel().getId());
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewBarProfileScene,LoggedUserData.getInstance().getUserModel().getId());
        }
    }

    private ObservableList<EventModelContainer> getAllEvents() {
        ObservableList<EventModelContainer> eventModels = FXCollections.observableArrayList();

        List<EventModel> allEvents = eventService.getAllEvents();
        for (EventModel eventModel : allEvents) {
            eventModels.add(new EventModelContainer(eventModel));
        }

        return eventModels;
    }

    private ObservableList<BarModelContainer> getAllBars() {
        ObservableList<BarModelContainer> barModels = FXCollections.observableArrayList();

        List<BarModel> allEvents = barService.getAllBars();
        for (BarModel barModel : allEvents) {
            barModels.add(new BarModelContainer(barModel));
        }

        return barModels;
    }

    private ObservableList<ArtistModelContainer> getAllArtists() {
        ObservableList<ArtistModelContainer> artistModels = FXCollections.observableArrayList();

        List<ArtistModel> allEvents = artistService.getAllArtists();
        for (ArtistModel artistModel : allEvents) {
            artistModels.add(new ArtistModelContainer(artistModel));
        }

        return artistModels;
    }
}
