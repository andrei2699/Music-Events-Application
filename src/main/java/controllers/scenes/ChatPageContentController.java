package controllers.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ChatPageContentController {
    @FXML
    public TableView discussionHeaderTableView;
    @FXML
    public TableColumn discussionHeaderTableColumn;
    public ListView messagesListView;
}
