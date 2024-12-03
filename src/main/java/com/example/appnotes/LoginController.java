package com.example.appnotes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                setCurrentUser(user);
                showMain(event);
            } else {
                System.out.println("Invalid username or password.");
            }
        } else {
            System.out.println("Username and password cannot be empty.");
        }
    }

    @FXML
    private void showRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("register-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load register view", e);
        }
    }

    private void showMain(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load main view", e);
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load login view", e);
        }
    }
}
