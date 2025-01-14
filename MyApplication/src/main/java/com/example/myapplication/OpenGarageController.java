package com.example.myapplication;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpenGarageController {
    @FXML
    private TextField NameTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private ImageView importImage;

    @FXML
    private TextField locationTextField;

    private Connection connectDB;

    @FXML
    public void initialize() {
        // Initialize the database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        connectDB = databaseConnection.getConnection();
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                importImage.setImage(image);
            } catch (Exception e) {
                showAlert("Error", "Could not load the image: " + e.getMessage());
            }
        } else {
            showAlert("No File Selected", "Please choose a valid image file.");
        }
    }

    @FXML
    public void addGarage() {
        try {
            // Validate inputs
            if (NameTextField.getText().trim().isEmpty() ||
                    locationTextField.getText().trim().isEmpty() ||
                    contactTextField.getText().trim().isEmpty() ||
                    importImage.getImage() == null) {
                showAlert("Validation Error", "All fields must be filled, and an image must be selected!");
                return;
            }

            // Prepare SQL
            String garageData = "INSERT INTO garagecarddata (garageName, garageLocation, garageContact, garageImage) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(garageData);

            // Set data
            statement.setString(1, NameTextField.getText());
            statement.setString(2, locationTextField.getText());
            statement.setString(3, contactTextField.getText());

            // Convert image to byte array
            Image image = importImage.getImage();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            statement.setBytes(4, imageBytes);

            // Execute query
            statement.executeUpdate();
            showAlert("Success", "Garage information added successfully!");

            // Clear fields
            clearFields();
        } catch (SQLException | IOException e) {
            showAlert("Error", "Could not save garage data: " + e.getMessage());
        }
    }

    private void clearFields() {
        NameTextField.clear();
        contactTextField.clear();
        locationTextField.clear();
        importImage.setImage(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void updateGarage() {
        try {
            // Validate inputs
            if (NameTextField.getText().trim().isEmpty() ||
                    locationTextField.getText().trim().isEmpty() ||
                    contactTextField.getText().trim().isEmpty()) {
                showAlert("Validation Error", "All fields must be filled to update the garage information!");
                return;
            }

            // Get the selected image, if updated
            byte[] imageBytes = null;
            if (importImage.getImage() != null) {
                Image image = importImage.getImage();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

//                // Resize the image (optional, based on your use case)
//                bufferedImage = resizeImage(bufferedImage, 500, 500);

                // Convert to byte array
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", outputStream);
                imageBytes = outputStream.toByteArray();
            }

            // Prepare SQL query
            String updateQuery = "UPDATE garagecarddata SET garageLocation = ?, garageContact = ?, garageImage = ? WHERE garageName = ?";

            // Execute query
            PreparedStatement statement = connectDB.prepareStatement(updateQuery);
            statement.setString(1, locationTextField.getText()); // Set updated location
            statement.setString(2, contactTextField.getText());  // Set updated contact number
            statement.setBytes(3, imageBytes);                  // Set updated image
            statement.setString(4, NameTextField.getText());    // Use garage name as the unique identifier

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Garage information updated successfully!");
            } else {
                showAlert("Update Failed", "No matching garage found with the provided name.");
            }

            // Clear input fields
            clearFields();

        } catch (SQLException | IOException e) {
            showAlert("Error", "Could not update garage information: " + e.getMessage());
        }
    }

}
