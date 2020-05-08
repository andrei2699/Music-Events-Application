package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.EventModel;
import models.UserType;

import java.io.IOException;

public class EventDetailsCardController extends TableCell<MainPageController.EventModelContainer, EventModel> {

    @FXML
    private VBox eventCardVBox;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label barNameLabel;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label startHourLabel;

    @FXML
    private Label numberOfSeatsLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TitledPane detailsTitlePane;

    @FXML
    private Button reserveTicketButton;

    @FXML
    private Separator actionButtonsSeparator;

    @FXML
    private VBox detailsTitledPaneContentVBox;

    @FXML
    private HBox numberOfSeatsHBox;

    @FXML
    private HBox actionButtonsHBox;

    public EventDetailsCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/eventDetailsCard.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(EventModel model, boolean empty) {
        super.updateItem(model, empty);

        if (!empty && model != null) {

            eventNameLabel.setText(model.getEventName());
            barNameLabel.setText(model.getBarName());
            artistNameLabel.setText(model.getArtistName());
            dateLabel.setText(model.getDate());
            startHourLabel.setText(model.getStartHour());
            numberOfSeatsLabel.setText(model.getNumberOfSeats() + "");
            descriptionLabel.setText(model.getDescription());

            reserveTicketButton.setOnAction(this::onReserveTicketButtonClick);

            Platform.runLater(() -> descriptionLabel.setPrefWidth(detailsTitledPaneContentVBox.getWidth()));

            if (!LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getType() != UserType.RegularUser) {
                hideControlsForNotRegisteredUsers();
            }

            setGraphic(eventCardVBox);
        } else {
            setGraphic(null);
        }

//        Platform.runLater(() -> {
//
//            Node plus = new Circle(10);
//            Node minus = new Circle(2);
//
//            detailsTitlePane.graphicProperty().bind(
//                    Bindings.when(detailsTitlePane.expandedProperty())
//                            .then(minus)
//                            .otherwise(plus));
//            detailsTitlePane.lookup(".arrow").setVisible(false);
//        });
    }

    public void hideControlsForNotRegisteredUsers() {
        eventCardVBox.getChildren().remove(actionButtonsHBox);
        eventCardVBox.getChildren().remove(actionButtonsSeparator);
        detailsTitledPaneContentVBox.getChildren().remove(numberOfSeatsHBox);
    }

    private void onReserveTicketButtonClick(ActionEvent actionEvent) {

    }
}
