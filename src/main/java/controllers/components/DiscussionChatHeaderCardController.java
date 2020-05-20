package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.cards.DiscussionHeaderCardModel;
import models.cards.TableCardModel;

import java.io.IOException;

import static main.ApplicationResourceStrings.DISCUSSION_HEADER_CHAT_CARD_FXML_PATH;

public class DiscussionChatHeaderCardController extends TableCell<TableCardModel, TableCardModel> {
    @FXML
    public ImageView profilePictureImage;
    @FXML
    public Label nameLabel;
    @FXML
    public HBox chatHeaderHBox;

    private ISceneResponseCall<DiscussionHeaderCardModel> onClickResponseCall;
    private DiscussionHeaderCardModel discussionHeaderCardModel;

    public DiscussionChatHeaderCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DISCUSSION_HEADER_CHAT_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(TableCardModel tableCardModel, boolean empty) {
        super.updateItem(tableCardModel, empty);

        if (!empty && tableCardModel != null) {

            discussionHeaderCardModel = (DiscussionHeaderCardModel) tableCardModel;

            chatHeaderHBox.setOnMouseClicked(this::onMouseClicked);

            setGraphic(chatHeaderHBox);
        } else {
            setGraphic(null);
        }
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (onClickResponseCall != null) {
            onClickResponseCall.onResponseCall(discussionHeaderCardModel);
        }
    }

    public void setOnClickResponseCall(ISceneResponseCall<DiscussionHeaderCardModel> onClickResponseCall) {
        this.onClickResponseCall = onClickResponseCall;
    }
}
