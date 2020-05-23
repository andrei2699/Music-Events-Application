package controllers.scenes;

import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.IUserService;
import services.ServiceProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.IMAGES_DEFAULT_USER_PHOTO_PATH;

public abstract class AbstractProfilePageController extends ChangeableSceneWithModelController {

    @FXML
    public ImageView profilePhoto;

    @FXML
    public ScheduleGridController scheduleGridController;

    protected IUserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceProvider.getUserService();
        updateUIOnInitialize();
    }

    protected abstract void updateUIOnInitialize();

    protected Image getProfileImage(String pathToImageFile) {
        if (pathToImageFile.isEmpty()) {
            try {
                pathToImageFile = Paths.get(getClass().getResource(IMAGES_DEFAULT_USER_PHOTO_PATH).toURI()).toString();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        File file = new File(pathToImageFile);
        return new Image(file.toURI().toString(), true);
    }
}
