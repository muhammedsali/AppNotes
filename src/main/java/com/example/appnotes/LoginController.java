package com.example.appnotes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;
    private static User currentUser;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            try {
                if (PasswordHash.verifyPassword(password, user.getPassword())) {
                    System.out.println("Login successful!");
                    currentUser = user;
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main-view.fxml"))));
                } else {
                    System.out.println("Login failed!");
                }
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login failed!");
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    @FXML
    private void showRegister() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("register-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        currentUser = null;
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login-view.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
