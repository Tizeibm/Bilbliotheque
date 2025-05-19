package com.POO.controller;

import com.POO.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookCardController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label publisherLabel;

    public void setBook(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText("Auteur : " + book.getAuthor());
        genreLabel.setText("Genre : " + book.getGenre());
        heightLabel.setText("Hauteur : " + book.getHeight() + " mm");
        publisherLabel.setText("Éditeur : " + book.getPublisher());
    }
}
