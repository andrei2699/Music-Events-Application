package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.EventCardModel;
import models.EventModel;
import services.EventService;
import services.ServiceProvider;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public abstract class ViewProfileAbstractController extends AbstractProfilePageController {
    private static final String NO_EVENTS_TABLE_VIEW_LABEL = "Fara evenimente viitoare";

    @FXML
    public Label nameLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public Label userTypeLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public TableView<EventModelContainer> eventsTableView;

    @FXML
    public TableColumn<EventModelContainer, EventCardModel> eventsTableColumn;

    @FXML
    public Button editProfilePageButton;

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        eventsTableView.setPlaceholder(new Label(NO_EVENTS_TABLE_VIEW_LABEL));
        eventsTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventCardModel"));
        eventsTableColumn.setCellFactory(cell -> new EventDetailsCardController());
        eventsTableView.setItems(getAllFutureEvents());
    }

    private ObservableList<EventModelContainer> getAllFutureEvents() {
        EventService eventService = ServiceProvider.getEventService();

        ObservableList<EventModelContainer> eventModels = FXCollections.observableArrayList();

        List<EventModel> allEvents = eventService.getAllEvents();
        for (EventModel eventModel : allEvents) {

            if (eventModel.getDate().compareTo(LocalDate.now()) >= 0 && eventModel.getStart_hour() > LocalTime.now().getHour()) {
                eventModels.add(new EventModelContainer(eventModel));
            }
        }

        return eventModels;
    }
}
