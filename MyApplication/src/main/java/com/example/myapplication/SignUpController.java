package com.example.myapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    Scene signIn;
    Stage stage;

    @FXML
    private Hyperlink SignIn;

    @FXML
    void onSignInClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
        signIn = new Scene(fxmlLoader.load());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(signIn);
        stage.show();
    }

}
