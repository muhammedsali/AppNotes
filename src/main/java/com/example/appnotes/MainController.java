package com.example.appnotes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML
    private void showNotes(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("notes-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load notes view", e);
        }
    }

    @FXML
    private void showBooks(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("books-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load books view", e);
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

    @FXML
    private void exportNotes(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Notes");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                NoteDAO noteDAO = new NoteDAO();
                User currentUser = LoginController.getCurrentUser();
                for (Note note : noteDAO.getNotesByUserId(currentUser.getId())) {
                    writer.println(note.getTitle() + ": " + note.getContent());
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to save notes", e);
            }
        }
    }

    @FXML
    private void importNotes(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Notes File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                NoteDAO noteDAO = new NoteDAO();
                User currentUser = LoginController.getCurrentUser();
                Files.lines(file.toPath()).forEach(line -> {
                    String[] parts = line.split(": ");
                    if (parts.length == 2) {
                        Note note = new Note();
                        note.setTitle(parts[0]);
                        note.setContent(parts[1]);
                        note.setUserId(currentUser.getId());
<<<<<<< HEAD
                        noteDAO.add(note);
=======
                        noteDAO.addNote(note);
>>>>>>> 5c36c321e7fc8c4690d939c2304bcef1a30bcdbb
                    }
                });
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to load notes", e);
            }
        }
    }
}
