package controllers.components;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class CrashReportPopupWindowController implements Initializable {
    @FXML
    public Label crashMessageLabel;

    @FXML
    public Button closeButton;

    private String crashMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crashMessageLabel.setText(crashMessage);
        Platform.runLater(crashMessageLabel::requestFocus);
        closeButton.setOnAction(this::onCloseButtonClick);
    }

    private void onCloseButtonClick(ActionEvent actionEvent) {
        crashMessageLabel.requestFocus();
        SceneSwitchController.getInstance().closeCrashReportPopup();
    }

    public void setCrashMessage(String crashMessage) {
        this.crashMessage = crashMessage;
    }
}
