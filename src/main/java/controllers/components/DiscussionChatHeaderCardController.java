package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.cards.TableCardModel;

import java.io.IOException;

import static main.ApplicationResourceStrings.DISCUSSION_HEADER_CHAT_CARD_FXML_PATH;

public class DiscussionChatHeaderCardController extends TableCell<TableCardModel, TableCardModel> {
    @FXML
    public ImageView profilePictureImage;
    @FXML
    public Label nameLabel;

    private ISceneResponseCall<Integer> onClickResponseCall;

    public DiscussionChatHeaderCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DISCUSSION_HEADER_CHAT_CARD_FXML_PATH));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (onClickResponseCall != null) {
            onClickResponseCall.onResponseCall(0);
        }
    }

    public void setOnClickResponseCall(ISceneResponseCall<Integer> onClickResponseCall) {
        this.onClickResponseCall = onClickResponseCall;
    }
}
