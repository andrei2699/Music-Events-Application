package controllers.components.cardsTableView;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ScrollEvent;
import models.cards.TableCardModel;
import utils.CardTableFiller;

public class CardsTableViewController {
    public TableView<TableCardModel> cardsTableView;

    public TableColumn<TableCardModel, TableCardModel> cardsTableColumn;

    public void setItems(ObservableList<TableCardModel> cardModels) {
        cardsTableView.setItems(cardModels);
        cardsTableView.addEventFilter(ScrollEvent.ANY, scrollEvent -> cardsTableView.refresh());
        Platform.runLater(() -> {
            for (Node node : cardsTableView.lookupAll(".scroll-bar")) {
                if (node instanceof ScrollBar && ((ScrollBar) node).getOrientation().equals(Orientation.VERTICAL)) {
                    ((ScrollBar) node).valueProperty().addListener(scrollEvent -> cardsTableView.refresh());
                }
            }
        });
    }

    public void setColumnData(DetailsTableConfigData detailsTableConfigData) {
        CardTableFiller.setTableData(cardsTableView, cardsTableColumn, detailsTableConfigData);
    }

    public TableCardModel getItem(int index) {
        return cardsTableView.getItems().get(index);
    }
}
