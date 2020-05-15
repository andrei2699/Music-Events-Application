package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.LoggedUserData;
import models.UserModel;
import models.cards.TableCardModel;
import services.ReservationService;
import services.ServiceProvider;
import utils.CardTableFiller;

import java.net.URL;
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

        ReservationService eventService = ServiceProvider.getReservationService();
        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        if (userModel != null) {
//            nameLabel.setText(userModel.getName());
//            List<ReservationModel> reservations = eventService.getReservations(userModel.getId());
//            reservationsTableView.setItems(reservations);
        }
    }
}
