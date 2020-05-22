package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.scheduleGrid.ReadonlyScheduleLoadStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.LoggedUserData;
import models.EventModel;
import models.UserModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.IEventService;
import services.ServiceProvider;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public CardsTableViewController eventsTableViewController;

    @FXML
    public Button editProfilePageButton;

    protected UserModel userModel;

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);

        eventsTableViewController.setColumnData(DetailsTableConfigData.getEventTableColumnData());

        scheduleGridController.setLoadStrategy(new ReadonlyScheduleLoadStrategy());
    }

    @Override
    public void onSetModelId(Integer modelId) {
        userModel = userService.getUser(modelId);
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
            eventsTableViewController.setItems(getAllFutureEventsLinkedWithId(userModel.getId()));
        }
    }

    protected final ObservableList<TableCardModel> getAllFutureEventsLinkedWithId(int id) {
        IEventService eventService = ServiceProvider.getEventService();
        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();
        List<EventModel> allEvents = eventService.getEventsStartingFrom(LocalDate.now(), LocalTime.now().getHour());

        for (EventModel eventModel : allEvents) {
            if (eventModel.getArtist_id() == id || eventModel.getBar_manager_id() == id) {
                eventModels.add(new EventCardModel(eventModel));
            }
        }

        return eventModels;
    }
}
