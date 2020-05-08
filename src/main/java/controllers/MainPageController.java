package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.EventCardModel;
import models.EventModel;
import models.UserModel;
import models.UserType;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ChangeableSceneController {

    private static final String NO_CONTENT_TABLE_VIEW_LABEL = "Fara evenimente";

    @FXML
    public TableView<EventModelContainer> eventsTableView;

    @FXML
    public TableColumn<EventModelContainer, EventCardModel> eventsTableColumn;

    @FXML
    private TextField searchTextField;

    private FilteredList<EventModelContainer> eventModelFilteredList;

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventModelFilteredList = new FilteredList<>(getMockupEvents(), m -> true);

        eventsTableView.setItems(eventModelFilteredList);
        eventsTableView.setPlaceholder(new Label(NO_CONTENT_TABLE_VIEW_LABEL));

        eventsTableColumn.setCellValueFactory(new PropertyValueFactory<>("eventCardModel"));
        eventsTableColumn.setCellFactory(cell -> new EventDetailsCardController());


//        for (EventModel model : eventModels) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/eventDetailsCard.fxml"));
//            EventDetailsCardController controller = new EventDetailsCardController(model);
//            loader.setController(controller);
//
////            try {
////                evenCardsContainer.getChildren().add(loader.load());
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//            if (!LoggedUserData.getInstance().isUserLogged() ||
//                    LoggedUserData.getInstance().getUserModel().getType() != UserType.RegularUser) {
//                controller.hideControlsForNotRegisteredUsers();
//            }
//        }


        searchTextField.textProperty().addListener(observable -> {
            String filter = searchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                eventModelFilteredList.setPredicate(m -> true);
            } else {
                eventModelFilteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });
    }

    public void goEditProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditArtistProfileScene);
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
        }
    }

    private ObservableList<EventModelContainer> getMockupEvents() {
        ObservableList<EventModelContainer> eventModels = FXCollections.observableArrayList();

        for (int i = 0; i < 20; i++) {
            EventCardModel model = new EventCardModel(new EventModel(i, 1, 2, "Event name " + i, "date " + i, i * 3, 10 * i));
            if (i % 2 == 0) {
                model.getEventModel().setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            }
            eventModels.add(new EventModelContainer(model));
        }

        return eventModels;
    }

    public static class EventModelContainer {
        private final EventCardModel eventCardModel;

        public EventModelContainer(EventCardModel eventModel) {
            this.eventCardModel = eventModel;
        }

        public EventCardModel getEventCardModel(){
            return eventCardModel;
        }

        public boolean containsFilter(String filter) {
            return eventCardModel.containsFilter(filter);
        }
    }
}
