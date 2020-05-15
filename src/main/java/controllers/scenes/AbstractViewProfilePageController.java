package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import models.cards.TableCardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.LoggedUserData;
import models.cards.EventCardModel;
import models.EventModel;
import models.UserModel;
import services.EventService;
import services.ServiceProvider;
import utils.CardTableFiller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractViewProfilePageController extends AbstractProfilePageController {

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
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);
        CardTableFiller.setTableData(eventsTableView, eventsTableColumn, DetailsTableConfigData.getEventTableColumnData());
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        userModel = userService.getUser(userModelId);
        updateUIOnInitialize();
    }

    @Override
    protected void updateUIOnInitialize() {
        boolean buttonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getId() != userModel.getId();
        editProfilePageButton.setVisible(!buttonInvisible);

        if (userModel != null) {
            nameLabel.setText(userModel.getName());
            userTypeLabel.setText(userModel.getType().toString());
            emailLabel.setText(userModel.getEmail());
            eventsTableView.setItems(getAllFutureEventsLinkedWithId(userModel.getId()));
        }
    }

    protected final ObservableList<TableCardModel> getAllFutureEventsLinkedWithId(int id) {
        EventService eventService = ServiceProvider.getEventService();

        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();

        List<EventModel> allEvents = eventService.getAllEvents();
        for (EventModel eventModel : allEvents) {

            if (eventModel.getArtist_id() == id || eventModel.getBar_manager_id() == id) {
                LocalDate parseLocalDate = LocalDate.parse(eventModel.getDate());
                if (parseLocalDate.compareTo(LocalDate.now()) > 0) {
                    eventModels.add(new EventCardModel(eventModel));
                } else if (parseLocalDate.compareTo(LocalDate.now()) == 0 && eventModel.getStart_hour() > LocalTime.now().getHour()) {
                    eventModels.add(new EventCardModel(eventModel));
                }
            }
        }

        return eventModels;
    }
}
