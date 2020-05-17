package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.LoggedUserData;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.cards.ReservationCardModel;
import models.cards.TableCardModel;
import services.IEventService;
import services.IReservationService;
import services.ServiceProvider;
import utils.CardTableFiller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class ViewRegularUserProfilePageController implements Initializable {
    @FXML
    public TableView<TableCardModel> reservationsTableView;

    @FXML
    public TableColumn<TableCardModel, TableCardModel> reservationsTableColumn;

    @FXML
    public Label nameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CardTableFiller.setTableData(reservationsTableView, reservationsTableColumn, DetailsTableConfigData.getReservationTableColumnData());

        IReservationService reservationService = ServiceProvider.getReservationService();
        IEventService eventService = ServiceProvider.getEventService();
        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        if (userModel != null) {
            nameLabel.setText(userModel.getName());

            ObservableList<TableCardModel> reservationCardModels = FXCollections.observableArrayList();

            List<ReservationModel> reservations = reservationService.getReservationUsingUserId(userModel.getId());
            List<EventModel> allFutureEvents = eventService.getEventsStartingFrom(LocalDate.now(), LocalTime.now().getHour());

            for (ReservationModel reservationModel : reservations) {
                if (allFutureEvents.stream().anyMatch(e->e.getId() == reservationModel.getEvent_id())) {
                    reservationCardModels.add(new ReservationCardModel(reservationModel));
                }
            }

            reservationsTableView.setItems(reservationCardModels);
        }
    }
}
