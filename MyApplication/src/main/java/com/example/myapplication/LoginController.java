package com.example.myapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class LoginController {
    Scene signUp;
    Stage stage;

    @FXML
    private Label wrongEmail;

    @FXML
    private Label wrongPass;

    @FXML
    private Hyperlink SignUp;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        switchToHomePage(event);
    }

    public boolean loginValidation(String email, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        boolean isValid = false;

        String verifyLogin = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement stmt = connectDB.prepareStatement(verifyLogin);

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                isValid = rs.next(); // If a record is found, credentials are valid
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }

    Scene homePage;

    private void switchToHomePage(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        if (loginValidation(email, password)) {
            // Switch to home page
            try {
                FXMLLoader homePageLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
                homePage = new Scene(homePageLoader.load());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(homePage);
                stage.setTitle("Home Page");
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Show error message
//            if (Objects.equals(emailTextField.getText(), "")){
//
//            }
            wrongEmail.setText("Wrong Email or Phone Number!");
            wrongPass.setText("Wrong Password!");
            //System.out.println("Invalid email or password.");
        }
    }


    @FXML
    void onSignUpClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUpPage.fxml"));
        signUp = new Scene(fxmlLoader.load());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(signUp);
        stage.show();
    }
}
