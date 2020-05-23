package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.DiscussionModel;
import services.IBarService;
import services.IDiscussionService;
import services.ServiceProvider;

import java.util.List;

public class ViewBarProfilePageController extends AbstractViewProfilePageController {

    private BarModel barModel;

    @FXML
    public Label addressLabel;

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        boolean startChatButtonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().isBarManager();
        startChatButton.setVisible(!startChatButtonInvisible);

        if (barModel != null) {
            addressLabel.setText(barModel.getAddress());
            profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
            scheduleGridController.setIntervals(barModel.getIntervals());
        }
    }

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.EditBarProfileContentScene);
    }

    @Override
    protected void onStartChatButtonClick(ActionEvent actionEvent) {
    }

    @Override
    public void onSetModelId(Integer modelId) {
        super.onSetModelId(modelId);

        IBarService barService = ServiceProvider.getBarService();
        barModel = barService.getBar(userModel.getId());
        updateUIOnInitialize();
    }
}
