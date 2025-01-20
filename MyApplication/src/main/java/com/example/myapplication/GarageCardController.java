package com.example.myapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class GarageCardController {
    @FXML
    private Label contact;

    @FXML
    private ImageView garageImage;

    @FXML
    private Label garageName;

    @FXML
    private Label location;

    @FXML
    private Label status;

    public void setData (GarageCard garageCardData) {

        garageName.setText(garageCardData.getGarageName());
        location.setText(garageCardData.getLocation());
        contact.setText(garageCardData.getContact());

        if (garageCardData.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(garageCardData.getImage()));
            garageImage.setImage(image);
        }
    }
}
