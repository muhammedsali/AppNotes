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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class BooksController {

    private static final Logger LOGGER = Logger.getLogger(BooksController.class.getName());
    private BookDAO bookDAO;
    private User currentUser;
    private Book selectedBook;

    @FXML
    private ListView<String> booksListView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;

    public BooksController() {
        bookDAO = new BookDAO();
    }

    @FXML
    private void initialize() {
        currentUser = LoginController.getCurrentUser();
        loadBooks();

        // Kitap seçildiğinde ayrıntılarını doldur
        booksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] parts = newValue.split(": ");
                if (parts.length == 2) {
                    titleField.setText(parts[0]);
                    authorField.setText(parts[1]);
                    selectedBook = bookDAO.getBookByTitleAndUserId(parts[0], currentUser.getId());
                }
            }
        });
    }

    @FXML
    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        if (!title.isEmpty() && !author.isEmpty()) {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setUserId(currentUser.getId());
            bookDAO.addBook(book);
            titleField.clear();
            authorField.clear();
            loadBooks();
            System.out.println("Book added: " + title);
        } else {
            System.out.println("Title and author cannot be empty.");
        }
    }

    @FXML
    private void editBook() {
        if (selectedBook != null) {
            String title = titleField.getText();
            String author = authorField.getText();
            if (!title.isEmpty() && !author.isEmpty()) {
                selectedBook.setTitle(title);
                selectedBook.setAuthor(author);
                bookDAO.updateBook(selectedBook);
                loadBooks();
                System.out.println("Book edited: " + title);
            } else {
                System.out.println("Title and author cannot be empty.");
            }
        } else {
            System.out.println("No book selected.");
        }
    }

    @FXML
    private void deleteBook() {
        if (selectedBook != null) {
            bookDAO.deleteBook(selectedBook.getId());
            titleField.clear();
            authorField.clear();
            loadBooks();
            System.out.println("Book deleted: " + selectedBook.getTitle());
        } else {
            System.out.println("No book selected.");
        }
    }

    private void loadBooks() {
        List<Book> books = bookDAO.getBooksByUserId(currentUser.getId());
        ObservableList<String> booksList = FXCollections.observableArrayList();
        for (Book book : books) {
            booksList.add(book.getTitle() + ": " + book.getAuthor());
        }
        booksListView.setItems(booksList);
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
