package com.example.myapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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

    private GarageCard garageCardData;

    public void setData (GarageCard garageCardData) {
        this.garageCardData = garageCardData;

        garageName.setText(garageCardData.getGarageName());
        location.setText(garageCardData.getLocation());
        contact.setText(garageCardData.getContact());
    }
}
