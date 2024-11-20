package com.example.appnotes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class NotesController {

    private static final Logger LOGGER = Logger.getLogger(NotesController.class.getName());
    private NoteDAO noteDAO;
    private User currentUser;
    private Note selectedNote;

    @FXML
    private ListView<String> notesListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField contentField;
    @FXML
    private Label feedbackLabel;

    public NotesController() {
        noteDAO = new NoteDAO();
    }

    @FXML
    private void initialize() {
        currentUser = LoginController.getCurrentUser();
        loadNotes();

        notesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] parts = newValue.split(": ");
                if (parts.length == 2) {
                    titleField.setText(parts[0]);
                    contentField.setText(parts[1]);
                    selectedNote = noteDAO.getNoteByTitleAndUserId(parts[0], currentUser.getId());
                }
            }
        });
    }

    @FXML
    private void addNote() {
        String title = titleField.getText();
        String content = contentField.getText();
        if (!title.isEmpty() && !content.isEmpty()) {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            note.setUserId(currentUser.getId());
            noteDAO.add(note);
            titleField.clear();
            contentField.clear();
            loadNotes();
            feedbackLabel.setText("Note added: " + title);
        } else {
            feedbackLabel.setText("Title and content cannot be empty.");
        }
    }

    @FXML
    private void editNote() {
        if (selectedNote != null) {
            String title = titleField.getText();
            String content = contentField.getText();
            if (!title.isEmpty() && !content.isEmpty()) {
                selectedNote.setTitle(title);
                selectedNote.setContent(content);
                noteDAO.update(selectedNote);
                loadNotes();
                feedbackLabel.setText("Note edited: " + title);
            } else {
                feedbackLabel.setText("Title and content cannot be empty.");
            }
        } else {
            feedbackLabel.setText("No note selected.");
        }
    }

    @FXML
    private void deleteNote() {
        if (selectedNote != null) {
            noteDAO.delete(selectedNote.getId());
            titleField.clear();
            contentField.clear();
            loadNotes();
            feedbackLabel.setText("Note deleted: " + selectedNote.getTitle());
        } else {
            feedbackLabel.setText("No note selected.");
        }
    }

    private void loadNotes() {
        List<Note> notes = noteDAO.getNotesByUserId(currentUser.getId());
        ObservableList<String> notesList = FXCollections.observableArrayList();
        for (Note note : notes) {
            notesList.add(note.getTitle() + ": " + note.getContent());
        }
        notesListView.setItems(notesList);
    }

    @FXML
    private void showMain(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main-view.fxml"))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load main view", e);
        }
    }
}
