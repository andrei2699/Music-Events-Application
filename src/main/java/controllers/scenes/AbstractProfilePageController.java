package controllers.scenes;

import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.IUserService;
import services.ServiceProvider;
import utils.StringValidator;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.IMAGE_DEFAULT_USER_PHOTO_PATH;

public abstract class AbstractProfilePageController extends ChangeableSceneWithModelController {

    @FXML
    public ImageView profilePhoto;

    @FXML
    public ScheduleGridController scheduleGridController;

    protected IUserService userService;

    public AbstractProfilePageController() {
        this(ServiceProvider.getUserService());
    }

    public AbstractProfilePageController(IUserService iUserService) {
        userService = iUserService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUIOnInitialize();
    }

    protected abstract void updateUIOnInitialize();

    protected Image getProfileImage(String pathToImageFile) {
        if (StringValidator.isStringEmpty(pathToImageFile)) {
            return new Image(getClass().getResourceAsStream(IMAGE_DEFAULT_USER_PHOTO_PATH));
        }

        try {
            File file = new File(pathToImageFile);
            return new Image(file.toURI().toString(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Image(getClass().getResourceAsStream(IMAGE_DEFAULT_USER_PHOTO_PATH));
    }
}