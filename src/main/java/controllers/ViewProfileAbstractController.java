package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.EventCardModel;

public abstract class ViewProfileAbstractController extends AbstractProfilePageController {

    @FXML
    public Label nameLabel;

    @FXML
    public Label addressLabel;

    @FXML
    public Label userTypeLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public TableView<EventModelContainer> eventsTableView;

    @FXML
    public TableColumn<EventModelContainer, EventCardModel> eventsTableColumn;

    @FXML
    public Button editProfilePageButton;

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);
}
