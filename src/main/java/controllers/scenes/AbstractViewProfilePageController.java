package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.cardsTableView.DetailsTableConfigData;
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
import services.IUserService;
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

    @FXML
    public Button startChatButton;

    protected UserModel userModel;

    private IEventService eventService;

    public AbstractViewProfilePageController() {
        eventService = ServiceProvider.getEventService();
    }

    public AbstractViewProfilePageController(IUserService iUserService) {
        super(iUserService);
        eventService = ServiceProvider.getEventService();
    }

    public AbstractViewProfilePageController(IUserService iUserService, IEventService eventService) {
        super(iUserService);
        this.eventService = eventService;
    }

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);

    protected abstract void onStartChatButtonClick(ActionEvent actionEvent);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);

        startChatButton.setOnAction(this::onStartChatButtonClick);

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
        boolean buttonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged() || (LoggedUserData.getInstance().isUserLogged() && LoggedUserData.getInstance().getUserModel().getId() != userModel.getId());
        editProfilePageButton.setVisible(!buttonInvisible);

        if (userModel != null) {
            nameLabel.setText(userModel.getName());
            userTypeLabel.setText(userModel.getType().toString());
            emailLabel.setText(userModel.getEmail());
            eventsTableViewController.setItems(getAllFutureEventsLinkedWithId(userModel.getId()));

            nameLabel.requestFocus();
        }
    }

    protected final ObservableList<TableCardModel> getAllFutureEventsLinkedWithId(int id) {
        ObservableList<TableCardModel> eventModels = FXCollections.observableArrayList();
        List<EventModel> allEvents = eventService.getEventsStartingFrom(LocalDate.now(), LocalTime.now().getHour());

        for (EventModel eventModel : allEvents) {
            if (eventModel.getArtist_id() == id || eventModel.getBar_manager_id() == id) {
                eventModels.add(new EventCardModel(eventModel, userService));
            }
        }

        return eventModels;
    }
}
