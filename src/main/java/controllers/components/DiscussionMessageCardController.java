package controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.cards.DiscussionMessageCardModel;
import models.cards.TableCardModel;
import models.other.Message;

import java.io.IOException;

import static main.ApplicationResourceStrings.DISCUSSION_MESSAGE_CARD_FXML_PATH;

public class DiscussionMessageCardController {
    @FXML
    public HBox messageCardHBox;
    @FXML
    public VBox messageVBox;
    @FXML
    public Label messageLabel;
    @FXML
    public Label dateLabel;

    public DiscussionMessageCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DISCUSSION_MESSAGE_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(DiscussionMessageCardModel discussionMessageCardModel) {
        Message message = discussionMessageCardModel.getMessage();

        messageLabel.setText(message.getText());
        dateLabel.setText(message.getDate());

        messageVBox.getStyleClass().clear();
        if (discussionMessageCardModel.isSender()) {
            messageVBox.getStyleClass().add("message-sender");
            messageCardHBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            messageVBox.getStyleClass().add("message-receiver");
            messageCardHBox.setAlignment(Pos.CENTER_LEFT);
        }
    }
}
