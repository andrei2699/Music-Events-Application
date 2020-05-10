package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardDetailsTableViewController implements Initializable {

    @FXML
    private ImageView searchImageView;

    @FXML
    private TableView<TableCardModel> tableView;

    @FXML
    private TableColumn<TableCardModel, TableCardModel> tableColumn;

    private TableColumnData columnData;

    private ObservableList<TableCardModel> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void updateTable() {
        tableView.setPlaceholder(new Label(columnData.getNoContentLabelText()));
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(columnData.getPropertyValueFactory()));
        tableColumn.setCellFactory(cell -> columnData.getCellFactory());
        tableColumn.setText(columnData.getTableColumnText());

        tableView.setItems(items);
    }

    public void setColumnData(TableColumnData columnData) {
        this.columnData = columnData;
    }

    public void setItems(ObservableList<TableCardModel> items) {
        this.items = items;
    }
}
