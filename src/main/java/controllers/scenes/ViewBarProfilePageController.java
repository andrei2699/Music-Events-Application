package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.SceneSwitchController;
import models.BarModel;
import services.BarService;
import services.ServiceProvider;

public class ViewBarProfilePageController extends AbstractViewProfilePageController {

    private BarModel barModel;

    @FXML
    public Label addressLabel;

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        if (barModel != null) {
            addressLabel.setText(barModel.getAddress());
            profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
        }
    }

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        super.onSetUserModelId(userModelId);

        BarService barService = ServiceProvider.getBarService();
        barModel = barService.getBar(userModel.getId());
        updateUIOnInitialize();
    }
}
