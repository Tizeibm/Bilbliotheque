package com.POO.controller;

import com.POO.model.Book;
import com.POO.db.BookDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType; // Pour la confirmation de suppression
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Optional;

public class BookViewController {

    @FXML
    private ListView<Book> bookListView;
    private ObservableList<Book> bookObservableList; // Utiliser ObservableList

    @FXML
    private FlowPane bookContainer;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField publisherField;

    @FXML
    public void initialize() {
        // Initialiser l'ObservableList
        bookObservableList = FXCollections.observableArrayList();
        // Lier la ListView à l'ObservableList
        bookListView.setItems(bookObservableList);

        // Charger les données initiales depuis la base de données
        chargeLivre();

        // Ajouter un listener pour remplir les champs quand un livre est sélectionné
        bookListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> champdeTexte(newValue)
        );
    }

    // Charger les livres depuis la DB et les ajouter à l'ObservableList
    private void chargeLivre() {
        bookObservableList.clear(); // Vider la liste avant de recharger
        bookObservableList.addAll(BookDAO.getAll());
        System.out.println("Livres charges depuis la base de donnees.");
        afficherCadreLivres();
    }

    // Remplir les champs de texte avec les données du livre sélectionné
    private void champdeTexte(Book book) {
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            genreField.setText(book.getGenre());
            heightField.setText(String.valueOf(book.getHeight())); // Convertir int en String
            publisherField.setText(book.getPublisher());
        } else {
            effacerChampTexte(); // Effacer les champs si rien n'est sélectionné
        }
    }


    @FXML
    private void AddBook() {
        // Valider les entrées
        String title = titleField.getText().trim(); // trim() pour enlever les espaces superflus
        String author = authorField.getText().trim();
        String genre = genreField.getText().trim();
        String publisher = publisherField.getText().trim();
        String heightText = heightField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty() || heightText.isEmpty()) {
            showAlert(AlertType.ERROR, "Informations Incompletes", "Veuillez remplir tous les champs.");
            return;
        }

        int height;
        try {
            height = Integer.parseInt(heightText);
            if (height <= 0) { // Validation supplémentaire
                showAlert(AlertType.ERROR, "Hauteur Invalide", "Veuillez entrer une hauteur positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Hauteur Invalide", "Veuillez entrer un nombre entier valide pour la hauteur.");
            return;
        }

        // Créer l'objet livre (sans ID pour l'instant)
        Book newBook = new Book(title, author, genre, height, publisher);

        // Insérer dans la base de données
        int generatedId = BookDAO.insert(newBook);

        if (generatedId != -1) {
            // Mettre à jour l'ID de l'objet et l'ajouter à la liste observable
            newBook.setId(generatedId);
            bookObservableList.add(newBook);
            showAlert(AlertType.INFORMATION, "Succes", "Livre ajoute avec succes !");
            effacerChampTexte();
            bookListView.getSelectionModel().select(newBook); // Sélectionner le nouveau livre
            afficherCadreLivres();//rafrachie les cardre
        } else {
            showAlert(AlertType.ERROR, "Erreur Base de Donnees", "Impossible d'ajouter le livre a la base de donnees.");
        }
    }

    @FXML
    private void deleteBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            // Confirmation avant suppression
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de Suppression");
            confirmationAlert.setHeaderText("Supprimer le livre : " + selectedBook.getTitle());
            confirmationAlert.setContentText("Etes-vous sur de vouloir supprimer ce livre ?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer de la base de données
                boolean deleted =BookDAO.delete(selectedBook.getId());
                if (deleted) {
                    // Supprimer de la liste observable
                    bookObservableList.remove(selectedBook);
                    showAlert(AlertType.INFORMATION, "Succes", "Livre supprimer avec succes !");
                    effacerChampTexte();
                } else {
                    showAlert(AlertType.ERROR, "Erreur Base de Donnees", "Impossible de supprimer le livre de la base de donnees.");
                }
            }
        } else {
            showAlert(AlertType.WARNING, "Aucune Selection", "Veuillez selectionner un livre a supprimer.");
        }
        afficherCadreLivres();//rafrachie les cardre
    }

    @FXML
    private void modifyBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(AlertType.WARNING, "Aucune Selection", "Veuillez selectionner un livre a modifier.");
            return;
        }

        // Valider les nouvelles entrées (similaire à handleAddBook)
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String genre = genreField.getText().trim();
        String publisher = publisherField.getText().trim();
        String heightText = heightField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty() || heightText.isEmpty()) {
            showAlert(AlertType.ERROR, "Informations Incompletes", "Veuillez remplir tous les champs pour la modification.");
            return;
        }

        int height;
        try {
            height = Integer.parseInt(heightText);
            if (height <= 0) {
                showAlert(AlertType.ERROR, "Hauteur Invalide", "Veuillez entrer une hauteur positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Hauteur Invalide", "Veuillez entrer un nombre entier valide pour la hauteur.");
            return;
        }

        // Créer un NOUVEL objet ou modifier l'objet existant ?
        // Il est souvent plus propre de créer un nouvel objet temporaire pour la mise à jour DB
        // ou de mettre à jour l'objet sélectionné directement AVANT l'appel DB.
        // Ici, mettons à jour l'objet sélectionné :
        selectedBook.setTitle(title);
        selectedBook.setAuthor(author);
        selectedBook.setGenre(genre);
        selectedBook.setHeight(height);
        selectedBook.setPublisher(publisher);

        // Mettre à jour dans la base de données
        boolean updated = BookDAO.update(selectedBook);

        if (updated) {
            // Rafraîchir l'affichage de l'élément dans la ListView
            // L'ObservableList devrait détecter le changement si Books_DB implémente correctement
            // les propriétés JavaFX, mais un refresh manuel est plus sûr ici.
            int index = bookListView.getSelectionModel().getSelectedIndex();
            bookObservableList.set(index, selectedBook); // Remplace l'élément à l'index

            showAlert(AlertType.INFORMATION, "Succes", "Livre modifie avec succes !");
            effacerChampTexte();
            bookListView.getSelectionModel().select(selectedBook); // Resélectionner
        } else {
            showAlert(AlertType.ERROR, "Erreur Base de Donnees",
                    "Impossible de modifier le livre dans la base de donnees.");
            // Optionnel : Recharger les données depuis la DB pour annuler les changements locaux
            // populateFields(DB.getBookById(selectedBook.getId())); // Nécessiterait une méthode getBookById
        }
        afficherCadreLivres();//rafrachie les cardre
    }

    @FXML
    private void effacerChampTexte() {
        titleField.clear();
        authorField.clear();
        genreField.clear();
        heightField.clear();
        publisherField.clear();
        // Désélectionner dans la liste
        bookListView.getSelectionModel().clearSelection();
    }

    // Méthode utilitaire pour afficher des alertes
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas d'en-tête
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void afficherCadreLivres() {
        bookContainer.getChildren().clear();

        for (Book book : bookObservableList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/POO/BookCard.fxml"));
                AnchorPane card = loader.load();

                BookCardController controller = loader.getController();
                controller.setBook(book);

                bookContainer.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}