package com.example.myapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class YourGarageController implements Initializable {
    @FXML
    private GridPane menuGridPane;

    @FXML
    private ScrollPane menuScrollPane;

    private ObservableList<GarageCard> cardListData = FXCollections.observableArrayList();

    public ObservableList<GarageCard> menuGetData() {
        String sql = "SELECT * FROM garagecarddata";

        ObservableList<GarageCard> listData = FXCollections.observableArrayList();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        try {
            PreparedStatement statement = connectDB.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            GarageCard garage;

            while (rs.next()) {
                garage = new GarageCard(rs.getString("garageName"),
                                        rs.getString("garageLocation"),
                                        rs.getString("garageContact"));

                listData.add(garage);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listData;
    }

    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menuGridPane.getRowConstraints().clear();
        menuGridPane.getColumnConstraints().clear();

        for(int q=0; q < cardListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("GarageCard.fxml"));
                AnchorPane pane = load.load();
                GarageCardController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if(column == 3) {
                    column = 0;
                    row += 1;
                }

                menuGridPane.add(pane, column++, row);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuDisplayCard();
    }
}
