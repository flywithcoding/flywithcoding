package com.example.myapplication;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class YourGarageController implements Initializable {

    @FXML
    private GridPane garageGridPane;

    @FXML
    private ScrollPane menuScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<GarageCard> garageCardList = loadGarageDataFromDatabase();
        System.out.println("Number of garage cards loaded: " + garageCardList.size());

        int columns = 0;
        int rows = 1;

        try {
            for (GarageCard garageCard : garageCardList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("GarageDesign.fxml"));

                VBox garageBox = fxmlLoader.load();

//                Node node = fxmlLoader.load();

                GarageCardController garageCardController = fxmlLoader.getController();
                garageCardController.setData(garageCard);

                if (columns == 3) { // Display 3 cards per row
                    columns = 0;
                    rows++;
                }

                garageGridPane.add(garageBox, columns++, rows);
                GridPane.setMargin(garageBox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur
        }
    }

    private List<GarageCard> loadGarageDataFromDatabase() {
        List<GarageCard> garageCards = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectionDB = null;

        try {
            connectionDB = databaseConnection.getConnection();
            String query = "SELECT garageName, garageLocation, garageContact, garageImage FROM garagecarddata";

            PreparedStatement preparedStatement = connectionDB.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                GarageCard garageCard = new GarageCard();
                garageCard.setGarageName(resultSet.getString("garageName"));
                garageCard.setLocation(resultSet.getString("garageLocation"));
                garageCard.setContact(resultSet.getString("garageContact"));

                byte[] imageBytes = resultSet.getBytes("garageImage");
                garageCard.setImageBytes(imageBytes);

                garageCards.add(garageCard);
                System.out.println("Loaded garage: " + garageCard.getGarageName());

            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connectionDB != null) {
                try {
                    connectionDB.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return garageCards;
    }
}
