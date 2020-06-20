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
import services.IUserService;
import services.ServiceProvider;
import utils.StringValidator;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
    private IUserService userService;
    private Integer modelId;
    private ObservableList<TableCardModel> discussionsCards;

    // for reflexion
    public ChatPageContentController() {
        this(ServiceProvider.getDiscussionService(), ServiceProvider.getUserService());
    }

    // for test
    protected ChatPageContentController(IDiscussionService discussionService, IUserService iUserService) {
        this.discussionService = discussionService;
        userService = iUserService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enterMessageTextField.requestFocus();

        final boolean[] focusSetOnFirstHeader = {false};

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
                        if (!focusSetOnFirstHeader[0]) {
                            focusSetOnFirstHeader[0] = true;
                            Platform.runLater(headerController::requestFocus);
                        }
                    }
                });
                return headerController;
            }
        });

        discussionsCards = FXCollections.observableArrayList();
        if (LoggedUserData.getInstance().isUserLogged()) {
            List<DiscussionModel> discussions = discussionService.getDiscussionsUsingId(LoggedUserData.getInstance().getUserModel().getId());
            for (DiscussionModel discussionModel : discussions) {
                discussionsCards.add(new DiscussionHeaderCardModel(discussionModel, userService));
            }
        }

        discussionHeaderTableViewController.setItems(discussionsCards);

        TableCardModel item = discussionHeaderTableViewController.getItem(0);
        if (item == null) {
            enterMessageTextField.setDisable(true);
            sendButton.setDisable(true);
        } else {
            switchConversation((DiscussionHeaderCardModel) item);
        }
    }

    @FXML
    public void onSendButtonClick(ActionEvent actionEvent) {
        if (openedHeaderCardModel != null && LoggedUserData.getInstance().isUserLogged() && StringValidator.isStringNotEmpty(enterMessageTextField.getText())) {
            enterMessageTextField.requestFocus();

            DiscussionModel discussionModel = openedHeaderCardModel.getDiscussionModel();
            LocalTime nowTime = LocalTime.now();
            LocalDate nowDate = LocalDate.now();
            String date = nowTime.getHour() + ":" + nowTime.getMinute() + " - " + nowDate.toString();
            discussionModel.addMessage(new Message(date, enterMessageTextField.getText(), LoggedUserData.getInstance().getUserModel().getId()));

            discussionService.updateDiscussion(discussionModel);
            switchConversation(openedHeaderCardModel);

            enterMessageTextField.setText("");

            Platform.runLater(() -> messagesScrollPane.setVvalue(1D));
        }
    }

    private void switchConversation(DiscussionHeaderCardModel headerCardModel) {
        if (headerCardModel == null || !LoggedUserData.getInstance().isUserLogged())
            return;

        openedHeaderCardModel = headerCardModel;

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
        Optional<TableCardModel> first = discussionsCards.stream().filter(i -> {
            if (i instanceof DiscussionHeaderCardModel) {
                return ((DiscussionHeaderCardModel) i).getDiscussionModel().getIds().contains(modelId);
            }
            return false;
        }).findFirst();

        first.ifPresent(tableCardModel -> {
            DiscussionHeaderCardModel model = (DiscussionHeaderCardModel) tableCardModel;
            switchConversation(model);
        });
    }
}