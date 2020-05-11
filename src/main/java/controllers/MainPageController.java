package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
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
    public TextField eventsSearchTextField;
    @FXML
    public TextField barSearchTextField;
    @FXML
    public TextField artistSearchTextField;

    @FXML
    public ImageView moreActionsImage;

    @FXML
    public CardDetailsTableViewCustomControl eventsCardDetailsTableViewControl;
    @FXML
    public CardDetailsTableViewCustomControl barsCardDetailsTableViewControl;
    @FXML
    public CardDetailsTableViewCustomControl artistsCardDetailsTableViewControl;

    private FilteredList<TableCardModel> eventModelFilteredList;
    private FilteredList<TableCardModel> barModelFilteredList;
    private FilteredList<TableCardModel> artistModelFilteredList;

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

        eventModelFilteredList = new FilteredList<>(getAllEvents(), m -> true);
        eventsSearchTextField.textProperty().addListener(observable -> {
            String filter = eventsSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                eventModelFilteredList.setPredicate(m -> true);
            } else {
                eventModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });

        eventsCardDetailsTableViewControl.setItems(eventModelFilteredList);
        eventsCardDetailsTableViewControl.setTableColumnData(new TableColumnData() {
            @Override
            public String getTableColumnText() {
                return "Evenimente Dispnibile";
            }

            @Override
            public String getPropertyValueFactory() {
                return "eventCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_EVENTS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new EventDetailsCardController();
            }

        });
        eventsCardDetailsTableViewControl.updateTable();


        // bars table view

        barModelFilteredList = new FilteredList<>(getAllBars(), m -> true);
        barSearchTextField.textProperty().addListener(observable -> {
            String filter = barSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                barModelFilteredList.setPredicate(m -> true);
            } else {
                barModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });

        barsCardDetailsTableViewControl.setItems(barModelFilteredList);
        barsCardDetailsTableViewControl.setTableColumnData(new TableColumnData() {
            @Override
            public String getTableColumnText() {
                return "Localuri";
            }

            @Override
            public String getPropertyValueFactory() {
                return "barCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_BARS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new BarDetailsCardController();
            }

        });
        barsCardDetailsTableViewControl.updateTable();

//         artists table view

        artistModelFilteredList = new FilteredList<>(getAllArtists(), m -> true);
        artistSearchTextField.textProperty().addListener(observable -> {
            String filter = artistSearchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                artistModelFilteredList.setPredicate(m -> true);
            } else {
                artistModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });

        artistsCardDetailsTableViewControl.setItems(artistModelFilteredList);
        artistsCardDetailsTableViewControl.setTableColumnData(new TableColumnData() {
            @Override
            public String getTableColumnText() {
                return "Artisti";
            }

            @Override
            public String getPropertyValueFactory() {
                return "artistCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_ARTISTS_TABLE_VIEW_LABEL;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new ArtistDetailsCardController();
            }

        });
        artistsCardDetailsTableViewControl.updateTable();
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
