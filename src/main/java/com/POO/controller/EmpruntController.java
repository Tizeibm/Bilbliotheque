package com.POO.controller;

import com.POO.db.BookDAO;
import com.POO.db.EmpruntDAO;
import com.POO.db.MemberDAO;
import com.POO.model.Book;
import com.POO.model.Emprunt;
import com.POO.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmpruntController {

    @FXML private ComboBox<Member> memberComboBox;
    @FXML private ComboBox<Book> bookComboBox;
    @FXML private DatePicker empruntDatePicker;
    @FXML private ListView<Emprunt> empruntListView;
    @FXML private ListView<Emprunt> returnedListView;
    @FXML private Button addEmpruntButton;

    private ObservableList<Member> memberList;
    private ObservableList<Book> bookList;
    private ObservableList<Emprunt> empruntList;
    private ObservableList<Emprunt> returnedList;

    @FXML
    public void initialize() {
        memberList = FXCollections.observableArrayList(MemberDAO.getAll());

        List<Emprunt> allEmprunts = EmpruntDAO.getAll();
        empruntList = FXCollections.observableArrayList(
                allEmprunts.stream()
                        .filter(e -> e.getReturnDate() == null)
                        .collect(Collectors.toList())
        );
        returnedList = FXCollections.observableArrayList(
                allEmprunts.stream()
                        .filter(e -> e.getReturnDate() != null)
                        .collect(Collectors.toList())
        );

        memberComboBox.setItems(memberList);
        empruntListView.setItems(empruntList);
        returnedListView.setItems(returnedList);

        empruntDatePicker.setValue(LocalDate.now());

        updateAvailableBooks();
    }

    private void updateAvailableBooks() {
        List<Book> allBooks = BookDAO.getAll();
        Set<Integer> borrowedBookIds = empruntList.stream()
                .map(e -> e.getBook().getId())
                .collect(Collectors.toSet());

        List<Book> availableBooks = allBooks.stream()
                .filter(book -> !borrowedBookIds.contains(book.getId()))
                .collect(Collectors.toList());

        bookList = FXCollections.observableArrayList(availableBooks);
        bookComboBox.setItems(bookList);
    }

    @FXML
    public void AddEmprunt() {
        Member selectedMember = memberComboBox.getValue();
        Book selectedBook = bookComboBox.getValue();
        LocalDate borrowDate = empruntDatePicker.getValue();

        if (selectedMember == null || selectedBook == null || borrowDate == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        Emprunt emprunt = new Emprunt(selectedMember, selectedBook, borrowDate);
        int id = EmpruntDAO.insert(emprunt);
        if (id != -1) {
            emprunt.setId(id);
            empruntList.add(emprunt);
            updateAvailableBooks();
            clearFields();
        } else {
            showAlert("Erreur lors de l’ajout de l’emprunt.");
        }
    }

    @FXML
    public void ReturnBook() {
        Emprunt selected = empruntListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un emprunt.");
            return;
        }

        if (selected.getReturnDate() != null) {
            showAlert("Ce livre a déjà été retourné.");
            return;
        }

        LocalDate today = LocalDate.now();
        boolean updated = EmpruntDAO.updateReturnDate(selected.getId(), today);
        if (updated) {
            selected.setReturnDate(today);
            empruntList.remove(selected);        // Retirer des emprunts en cours
            returnedList.add(selected);          // Ajouter aux livres retournés
            updateAvailableBooks();              // Mettre à jour les livres disponibles
        } else {
            showAlert("Erreur lors de la mise à jour du retour.");
        }
    }

    private void clearFields() {
        memberComboBox.getSelectionModel().clearSelection();
        bookComboBox.getSelectionModel().clearSelection();
        empruntDatePicker.setValue(LocalDate.now());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
