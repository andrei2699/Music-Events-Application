package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import main.SceneSwitchController;
import models.BarCardModel;
import models.BarModel;

import java.io.IOException;

public class BarDetailsCardController extends TableCell<TableCardModel, TableCardModel> {

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
    protected void updateItem(TableCardModel tableCardModel, boolean empty) {
        super.updateItem(tableCardModel, empty);

        if (!empty && tableCardModel != null) {
            BarCardModel barCardModel = (BarCardModel) tableCardModel;
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
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewBarProfileScene, barModel.getUser_id());
    }

}
