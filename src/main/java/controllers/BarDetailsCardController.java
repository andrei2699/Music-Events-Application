package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarCardModel;
import models.BarModel;
import models.EventModel;
import models.UserType;

import java.io.IOException;

public class BarDetailsCardController extends TableCell<BarModelContainer, BarCardModel> {

    @FXML
    private VBox barDetailsCardVBox;

    @FXML
    private Label barNameLabel;

    @FXML
    private Label adressLabel;

    @FXML
    private Button goToProfilePageButton;

    private BarModel barModel;

    public BarDetailsCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/barDetailsCard.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(BarCardModel barCardModel, boolean empty) {
        super.updateItem(barCardModel, empty);

        if (!empty && barCardModel != null) {
            barModel = barCardModel.getBarModel();
            barNameLabel.setText(barCardModel.getBarName());
            adressLabel.setText(barCardModel.getBarModel().getAddress());

            goToProfilePageButton.setOnAction(this::onGoToProfilePageButtonClick);


            setGraphic(barDetailsCardVBox);
        } else {
            setGraphic(null);
        }
    }

    private void onGoToProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.BarProfileScene,barModel.getUser_id());
    }

}
