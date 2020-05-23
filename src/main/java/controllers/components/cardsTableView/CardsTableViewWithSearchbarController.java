package controllers.components.cardsTableView;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.cards.TableCardModel;
import utils.CardTableFiller;

public class CardsTableViewWithSearchbarController {

    @FXML
    public CardsTableViewController cardsTableViewController;

    @FXML
    public TextField searchTextField;

    @FXML
    public void onSearchImageClicked(MouseEvent mouseEvent) {
        searchTextField.requestFocus();
    }

    public void setupTableData(String searchBarText, FilteredList<TableCardModel> modelFilteredList, DetailsTableConfigData tableColumnData) {
        searchTextField.setPromptText(searchBarText);
        cardsTableViewController.setItems(modelFilteredList);
        cardsTableViewController.setColumnData(tableColumnData);
        CardTableFiller.linkSearchFieldAndFilteredList(searchTextField, modelFilteredList);
    }
}
