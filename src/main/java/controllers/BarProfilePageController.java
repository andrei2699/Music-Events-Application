package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BarProfilePageController implements Initializable {

    @FXML
    public ImageView profilePhoto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        File file=new File("C:/Users/Tania/Desktop/patrat.png");
//        profilePhoto.setImage(new Image(file.toURI().toString()));
    }
}
