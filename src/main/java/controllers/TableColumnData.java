package controllers;

import javafx.scene.control.TableCell;

public abstract class TableColumnData {

    public abstract String getTableColumnText();

    public abstract String getPropertyValueFactory();

    public abstract String getNoContentLabelText();

    public abstract TableCell<TableCardModel, TableCardModel> getCellFactory();
}
