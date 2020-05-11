package utils;

import controllers.DetailsTableConfigData;
import controllers.TableCardModel;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public final class CardTableFiller {

    private CardTableFiller() {
    }

    public static void setupTable(TextField searchTextField, FilteredList<TableCardModel> modelFilteredList, TableView<TableCardModel> tableView,
                                  TableColumn<TableCardModel, TableCardModel> tableColumn, DetailsTableConfigData detailsTableConfigData) {

        linkSearchFieldAndFilteredList(searchTextField, modelFilteredList);
        setTableData(tableView, tableColumn, detailsTableConfigData);
        tableView.setItems(modelFilteredList);
    }

    public static void setTableData(TableView<TableCardModel> tableView, TableColumn<TableCardModel, TableCardModel> tableColumn, DetailsTableConfigData columnData) {
        tableView.setPlaceholder(new Label(columnData.getNoContentLabelText()));
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(columnData.getPropertyValueFactory()));
        tableColumn.setCellFactory(cell -> columnData.getCellFactory());
        tableColumn.setText(columnData.getTableColumnText());
    }

    public static void linkSearchFieldAndFilteredList(TextField searchTextField, FilteredList<TableCardModel> filteredList) {
        searchTextField.textProperty().addListener(observable -> {
            String filter = searchTextField.getText();
            if (filter == null || filter.isEmpty() || filter.isBlank()) {
                filteredList.setPredicate(m -> true);
            } else {
                filteredList.setPredicate(m -> m.containsFilter(filter));
            }
        });
    }
}
