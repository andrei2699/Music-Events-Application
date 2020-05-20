package controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import models.other.DiscussionMessageModel;

import java.io.IOException;

import static main.ApplicationResourceStrings.DISCUSSION_MESSAGE_CARD_FXML_PATH;

public class DiscussionMessageCardController extends ListCell<DiscussionMessageModel> {
    @FXML
    public HBox messageHBox;
    @FXML
    public Label messageLabel;
    @FXML
    public Label dateLabel;

    private Integer senderId;

    public DiscussionMessageCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DISCUSSION_MESSAGE_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(DiscussionMessageModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            messageLabel.setText(item.getText());
            dateLabel.setText(item.getDate());

            if (item.getSenderId() == senderId) {
                messageHBox.getStyleClass().add("message-sender");
            } else {
                messageHBox.getStyleClass().add("message-receiver");
            }

            setGraphic(messageHBox);
        }
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
}
