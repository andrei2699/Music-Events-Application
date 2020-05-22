package controllers.scenes;

import controllers.components.DetailsTableConfigData;
import controllers.components.DiscussionChatHeaderCardController;
import controllers.components.DiscussionMessageCardController;
import controllers.components.cardsTableView.CardsTableViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import models.DiscussionMessageModel;
import models.DiscussionModel;
import models.cards.DiscussionHeaderCardModel;
import models.cards.DiscussionMessageCardModel;
import models.cards.TableCardModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.CONVERSATION_WITH_TEXT;

public class ChatPageContentController implements Initializable {
    @FXML
    public CardsTableViewController discussionHeaderTableViewController;

    @FXML
    public CardsTableViewController messagesTableViewController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        discussionHeaderTableViewController.setColumnData(new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return "Conversatii";
            }

            @Override
            public String getPropertyValueFactory() {
                return "discussionHeaderCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara conversatii";
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                DiscussionChatHeaderCardController headerController = new DiscussionChatHeaderCardController();
                headerController.setOnClickResponseCall(headerCardModel -> switchConversation(headerCardModel));
                return headerController;
            }
        });
        messagesTableViewController.setColumnData(new DetailsTableConfigData() {
            @Override
            public String getTableColumnText() {
                return "Conversatie";
            }

            @Override
            public String getPropertyValueFactory() {
                return "discussionMessageCardModel";
            }

            @Override
            public String getNoContentLabelText() {
                return "Fara mesaje";
            }

            @Override
            public TableCell<TableCardModel, TableCardModel> getCellFactory() {
                return new DiscussionMessageCardController();
            }
        });

        ObservableList<TableCardModel> discussions = FXCollections.observableArrayList();
        discussions.add(new DiscussionHeaderCardModel(new DiscussionModel(1, 21, 19)));
        discussions.add(new DiscussionHeaderCardModel(new DiscussionModel(2, 4, 9)));
        discussions.add(new DiscussionHeaderCardModel(new DiscussionModel(3, 16, 27)));
        discussionHeaderTableViewController.setItems(discussions);
//        discussionHeaderTableView.setItems();
//        switchConversation(1);
    }

    private void switchConversation(DiscussionHeaderCardModel headerCardModel) {
        List<DiscussionMessageModel> messages = new ArrayList<>();
        messages.add(new DiscussionMessageModel("05-06-2020", "Salut", true));
        messages.add(new DiscussionMessageModel("05-06-2020", "Ce faci ?", true));
        messages.add(new DiscussionMessageModel("05-06-2020", "Bine", false));
        messages.add(new DiscussionMessageModel("05-06-2020", "Ma bucur", true));
        messages.add(new DiscussionMessageModel("05-07-2020", "Azi e soare afara", false));
        messages.add(new DiscussionMessageModel("05-07-2020", "Intr-adevar", false));
        messages.add(new DiscussionMessageModel("05-07-2020", "Super", true));

        ObservableList<TableCardModel> discussionMessageModels = FXCollections.observableArrayList();
        for (DiscussionMessageModel messageModel : messages) {
            discussionMessageModels.add(new DiscussionMessageCardModel(messageModel));
        }

        messagesTableViewController.setColumnText(CONVERSATION_WITH_TEXT + " " + headerCardModel.toString());
        messagesTableViewController.clearItems();
        messagesTableViewController.setItems(discussionMessageModels);
        messagesTableViewController.scrollTo(discussionMessageModels.size() - 1);
    }
}
