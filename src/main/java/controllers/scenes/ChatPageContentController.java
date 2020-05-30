package controllers.scenes;

import controllers.components.DiscussionChatHeaderCardController;
import controllers.components.DiscussionMessageCardController;
import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.cardsTableView.DetailsTableConfigData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.DiscussionModel;
import models.cards.DiscussionHeaderCardModel;
import models.cards.DiscussionMessageCardModel;
import models.cards.TableCardModel;
import models.other.Message;
import services.IDiscussionService;
import services.ServiceProvider;
import utils.StringValidator;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.*;

public class ChatPageContentController extends ChangeableSceneWithModelController {
    @FXML
    public CardsTableViewController discussionHeaderTableViewController;
    @FXML
    public TextField enterMessageTextField;
    @FXML
    public Button sendButton;
    @FXML
    public VBox messagesVBox;
    @FXML
    public ScrollPane messagesScrollPane;
    @FXML
    public Label conversationWithLabel;

    private DiscussionHeaderCardModel openedHeaderCardModel;

    private IDiscussionService discussionService;

    private Integer modelId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        discussionService = ServiceProvider.getDiscussionService();
        enterMessageTextField.requestFocus();

        discussionHeaderTableViewController.setColumnData(new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return CONVERSATIONS_TEXT;
            }

            @Override
            public String getPropertyValueFactory() {
                return "discussionHeaderCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return NO_CONVERSATIONS_TEXT;
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                DiscussionChatHeaderCardController headerController = new DiscussionChatHeaderCardController();
                headerController.setOnClickResponseCall(headerCardModel -> switchConversation(headerCardModel));
                headerController.setOnCardModelSet((discussionHeaderCardModel) -> {
                    if (modelId != null && headerController.hasModelId(modelId)) {
                        Platform.runLater(headerController::requestFocus);
                    }
                });
                return headerController;
            }
        });

        ObservableList<TableCardModel> discussionsCards = FXCollections.observableArrayList();
        if (LoggedUserData.getInstance().isUserLogged()) {
            List<DiscussionModel> discussions = discussionService.getDiscussionsUsingId(LoggedUserData.getInstance().getUserModel().getId());
            for (DiscussionModel discussionModel : discussions) {
                discussionsCards.add(new DiscussionHeaderCardModel(discussionModel));
            }
        }

        discussionHeaderTableViewController.setItems(discussionsCards);

        try {
            switchConversation((DiscussionHeaderCardModel) discussionHeaderTableViewController.getItem(0));
        } catch (IndexOutOfBoundsException ignored) {
            enterMessageTextField.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    @FXML
    public void onSendButtonClick(ActionEvent actionEvent) {
        if (openedHeaderCardModel != null && LoggedUserData.getInstance().isUserLogged() && StringValidator.isStringNotEmpty(enterMessageTextField.getText())) {
            DiscussionModel discussionModel = openedHeaderCardModel.getDiscussionModel();
            LocalTime nowTime = LocalTime.now();
            LocalDate nowDate = LocalDate.now();
            String date = nowTime.getHour() + ":" + nowTime.getMinute() + " - " + nowDate.toString();
            discussionModel.addMessage(new Message(date, enterMessageTextField.getText(), LoggedUserData.getInstance().getUserModel().getId()));

            discussionService.updateDiscussion(discussionModel);
            switchConversation(openedHeaderCardModel);

            enterMessageTextField.setText("");
        }
    }

    private void switchConversation(DiscussionHeaderCardModel headerCardModel) {
        openedHeaderCardModel = headerCardModel;
        if (headerCardModel == null || !LoggedUserData.getInstance().isUserLogged())
            return;

        List<Message> messages = headerCardModel.getDiscussionModel().getMessages();

        messagesVBox.getChildren().clear();

        for (Message message : messages) {
            boolean isSender = message.getSender_id() == LoggedUserData.getInstance().getUserModel().getId();
            if (!isSender) {
                message.setSeen(true);
            }

            DiscussionMessageCardController controller = new DiscussionMessageCardController();
            controller.updateItem(new DiscussionMessageCardModel(message, isSender));

            messagesVBox.getChildren().add(controller.messageCardHBox);
        }

        discussionService.updateDiscussion(headerCardModel.getDiscussionModel());
        conversationWithLabel.setText(CONVERSATION_WITH_TEXT + " " + headerCardModel.toString());
        messagesScrollPane.setVvalue(1D);
    }

    @Override
    public void onSetModelId(Integer modelId) {
        this.modelId = modelId;
    }
}
