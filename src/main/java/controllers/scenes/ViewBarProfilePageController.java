package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import services.IBarService;
import services.IDiscussionService;
import services.IUserService;
import services.ServiceProvider;

public class ViewBarProfilePageController extends AbstractViewProfilePageController {

    @FXML
    public Label addressLabel;

    private BarModel barModel;
    private IBarService barService;
    private IDiscussionService discussionService;

    //pentru apelul prin reflexie
    public ViewBarProfilePageController() {
        barService = ServiceProvider.getBarService();
        discussionService = ServiceProvider.getDiscussionService();
    }

    //pentru testare
    protected ViewBarProfilePageController(IUserService iUserService, IBarService iBarService, IDiscussionService discussionService) {
        super(iUserService);
        barService = iBarService;
        this.discussionService = discussionService;
    }

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        boolean startChatButtonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged()
                || LoggedUserData.getInstance().isRegularUser() || LoggedUserData.getInstance().isBarManager();
        startChatButton.setVisible(!startChatButtonInvisible);

        boolean gridInvisible = userModel == null || LoggedUserData.getInstance().isRegularUser() || (LoggedUserData.getInstance().isBarManager() && LoggedUserData.getInstance().getUserModel().getId() != userModel.getId());
        scheduleGridController.setVisible(!gridInvisible);


        if (barModel != null) {
            addressLabel.setText(barModel.getAddress());
            profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
            scheduleGridController.setIntervals(barModel.getIntervals());
        }
    }

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        addressLabel.requestFocus();
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.EditBarProfileContentScene);
    }

    @Override
    protected void onStartChatButtonClick(ActionEvent actionEvent) {
        addressLabel.requestFocus();
        if (barModel != null && LoggedUserData.getInstance().isUserLogged()) {
            discussionService.createDiscussion(barModel.getId(), LoggedUserData.getInstance().getUserModel().getId());
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ChatContentScene, barModel.getId());
        }
    }

    @Override
    public void onSetModelId(Integer modelId) {
        super.onSetModelId(modelId);

        barModel = barService.getBar(userModel.getId());
        updateUIOnInitialize();
    }
}
