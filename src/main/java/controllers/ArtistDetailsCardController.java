package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.SceneSwitchController;
import models.ArtistCardModel;
import models.ArtistModel;
import models.BarCardModel;
import models.BarModel;

import java.io.IOException;

public class ArtistDetailsCardController extends TableCell<TableCardModel, TableCardModel> {

    @FXML
    private VBox artistDetailsCardVBox;

    @FXML
    private Label artistNameLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label bandMemebersLabel;

    @FXML
    private HBox bandMembersHBox;

    @FXML
    private Button goToProfilePageButton;

    private ArtistModel artistModel;

    public ArtistDetailsCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/components/artistDetailsCard.fxml"));
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
          ArtistCardModel artistCardModel = (ArtistCardModel) tableCardModel;
            artistModel = artistCardModel.getArtistModel();
            artistNameLabel.setText(artistCardModel.getArtistName());
            genreLabel.setText(artistCardModel.getArtistModel().getGenre());

            boolean isBand = artistCardModel.getArtistModel().getIs_band();
            if(isBand)
            {
                bandMembersHBox.setVisible(true);
                bandMemebersLabel.setText(artistCardModel.getArtistModel().getMembers());
            }
            else
            {
                bandMembersHBox.setVisible(false);
            }

            goToProfilePageButton.setOnAction(this::onGoToProfilePageButtonClick);


            setGraphic(artistDetailsCardVBox);
        } else {
            setGraphic(null);
        }
    }

    private void onGoToProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.ViewArtistProfileScene,artistModel.getUser_id());
    }
}
