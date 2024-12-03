package com.example.appnotes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    public RegisterController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userDAO.add(user);
            System.out.println("Registration successful!");
            showLogin();
        } else {
            System.out.println("Username and password cannot be empty.");
        }
    }

    @FXML
    private void showLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
