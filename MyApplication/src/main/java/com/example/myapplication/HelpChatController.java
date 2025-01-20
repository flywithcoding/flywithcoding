// Updated ChatClient with FXML integration and threading
package com.example.myapplication;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class HelpChatController {

    @FXML
    private TextArea messageDisplayBox;

    @FXML
    private TextField sendMessageBox;

    @FXML
    private Button messageSendBtn;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void initialize() {
        connectToServer();

        // Configure send button action
        messageSendBtn.setOnAction(event -> sendMessage());
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to read messages from the server
            Thread messageReaderThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> messageDisplayBox.appendText(finalMessage + "\n"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messageReaderThread.setDaemon(true);
            messageReaderThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = sendMessageBox.getText();
        if (!message.isEmpty()) {
            writer.println(message);
            sendMessageBox.clear();
        }
    }

    // Ensure resources are cleaned up when the application exits
    public void closeConnection() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
