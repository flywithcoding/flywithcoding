package com.example.myapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HomePageController {
    @FXML
    private Button imageImport;

    @FXML
    private ImageView imageShow;

    @FXML
    private AnchorPane innerAnchorPane;

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );

        // Show the FileChooser dialog
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                // Load and display the image
                Image image = new Image(file.toURI().toString());
                imageShow.setImage(image);
            } catch (Exception e) {
                showAlert("Error", "Could not load the image: " + e.getMessage());
            }
        } else {
            showAlert("No File Selected", "Please choose a valid image file.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void toggleVisibility(ActionEvent event) {
        innerAnchorPane.setVisible(!innerAnchorPane.isVisible());
    }
}

