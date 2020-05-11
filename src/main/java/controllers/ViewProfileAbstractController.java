package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.LoggedUserData;
import models.EventCardModel;
import models.EventModel;
import models.UserModel;
import services.ArtistService;
import services.EventService;
import services.ServiceProvider;
import utils.CardTableFiller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public abstract class ViewProfileAbstractController extends AbstractProfilePageController {

    @FXML
    public Label nameLabel;

    @FXML
    public Label userTypeLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public TableView<TableCardModel> eventsTableView;

    @FXML
    public TableColumn<TableCardModel, TableCardModel> eventsTableColumn;

    @FXML
    public Button editProfilePageButton;

    protected UserModel userModel;

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        CardTableFiller.setTableData(eventsTableView, eventsTableColumn, DetailsTableConfigData.getEventTableColumnData());
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        userModel = userService.getUser(userModelId);
        updateUIOnSceneChanged();
    }

    @Override
    protected void updateUIOnSceneChanged() {
        boolean buttonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getId() != userModel.getId();
        editProfilePageButton.setVisible(!buttonInvisible);
    }

    protected final ObservableList<TableCardModel> getAllFutureEventsLinkedWithId(int id) {
        EventService eventService = ServiceProvider.getEventService();

        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();

        List<EventModel> allEvents = eventService.getAllEvents();
        for (EventModel eventModel : allEvents) {

            if (eventModel.getArtist_id() == id || eventModel.getBar_manager_id() == id) {
                if (eventModel.getDate().compareTo(LocalDate.now()) >= 0 && eventModel.getStart_hour() > LocalTime.now().getHour()) {
                    eventModels.add(new EventCardModel(eventModel));
                }
            }
        }

        return eventModels;
    }
}
