package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.cards.TableCardModel;
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
    }
}
