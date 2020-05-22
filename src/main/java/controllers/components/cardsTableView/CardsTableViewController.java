package controllers.components.cardsTableView;

import controllers.components.DetailsTableConfigData;
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

    public void clearItems() {
        cardsTableView.getItems().clear();
    }

    public void scrollTo(int index) {
        cardsTableView.scrollTo(index);
    }

    public void setColumnText(String text) {
        cardsTableColumn.setText(text);
    }
}
