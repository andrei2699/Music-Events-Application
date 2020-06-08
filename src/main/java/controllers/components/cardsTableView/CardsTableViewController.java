package controllers.components.cardsTableView;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.cards.TableCardModel;
import utils.CardTableFiller;

public class CardsTableViewController {
    public TableView<TableCardModel> cardsTableView;

    public TableColumn<TableCardModel, TableCardModel> cardsTableColumn;

    public void setItems(ObservableList<TableCardModel> cardModels) {
        cardsTableView.setItems(cardModels);
    }

    public void setColumnData(DetailsTableConfigData detailsTableConfigData) {
        CardTableFiller.setTableData(cardsTableView, cardsTableColumn, detailsTableConfigData);
    }

    public TableCardModel getItem(int index) {
        return cardsTableView.getItems().get(index);
    }
}
