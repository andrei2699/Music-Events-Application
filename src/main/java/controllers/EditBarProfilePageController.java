package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import main.LoggedUserData;
import models.BarModel;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;
import services.UserService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditBarProfilePageController implements Initializable {
    @FXML
    public ImageView profilePhoto;

    @FXML
    public TextField userNameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    @FXML
    public TextField addressField;

    @FXML
    public GridPane scheduleGridPane;

    private UserService userService;
    private BarService barService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = ServiceProvider.getUserService();
        barService = ServiceProvider.getBarService();

        fillFieldsWithValuesFromLoggedUserData();

        // make a save to the database in case something is wrong
        onSaveChangesButtonClick(null);
    }

    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {

    }

    public void onSaveChangesButtonClick(ActionEvent actionEvent) {

    }

    private void fillFieldsWithValuesFromLoggedUserData() {
        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        userNameField.setText(userModel.getName());
        emailField.setText(userModel.getEmail());
        BarModel barModel = barService.getBar(userModel.getId());
        addressField.setText(barModel.getAddress());

        File file = new File(barModel.getPath_to_image());
        profilePhoto.setImage(new Image(file.toURI().toString()));
    }
}
