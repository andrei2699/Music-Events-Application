package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CardDetailsTableViewCustomControl extends VBox {

    private final CardDetailsTableViewController controller;

    public CardDetailsTableViewCustomControl() {
        super();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/components/cardDetailsTableView.fxml"));

        controller = new CardDetailsTableViewController();
        loader.setController(controller);

        try {
            Parent root = loader.load();
            getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTableColumnData(TableColumnData tableModel) {
        controller.setColumnData(tableModel);
    }

    public void setItems(ObservableList<TableCardModel> items) {
        controller.setItems(items);
    }

    public void updateTable() {
        this.controller.updateTable();
    }
}
