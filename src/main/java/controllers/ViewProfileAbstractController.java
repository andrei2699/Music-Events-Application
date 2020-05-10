package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.EventCardModel;

public abstract class ViewProfileAbstractController extends AbstractProfilePageController {
//    @FXML
//    public TextField nameField;
//
//    @FXML
//    public TextField emailField;
//
//    @FXML
//    public TextField userTypeField;

    @FXML
    public TableView<EventModelContainer> eventsTableView;

    @FXML
    public TableColumn<EventModelContainer, EventCardModel> eventsTableColumn;

    @FXML
    public Button editProfilePageButton;

    protected abstract void onEditProfilePageButtonClick(ActionEvent actionEvent);
}
