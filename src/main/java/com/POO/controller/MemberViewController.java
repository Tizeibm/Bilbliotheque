package com.POO.controller;

import com.POO.db.MemberDAO;
import com.POO.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MemberViewController {

    @FXML private ListView<Member> memberListView;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> typeComboBox;

    private ObservableList<Member> memberList;

    @FXML
    public void initialize() {
        // Initialiser le ComboBox avec les types
        typeComboBox.setItems(FXCollections.observableArrayList("etudiant", "enseignant"));

        // Charger les membres depuis la base de données
        memberList = FXCollections.observableArrayList(MemberDAO.getAll());
        memberListView.setItems(memberList);

        // Sélection dans la liste
        memberListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nameField.setText(newVal.getName());
                emailField.setText(newVal.getEmail());
                typeComboBox.setValue(newVal.getType());
            }
        });
    }

    @FXML
    private void handleAddMember() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String type = typeComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || type == null) {
            showAlert("Tous les champs doivent être remplis.");
            return;
        }

        Member member = new Member(name, email, type);
        int id = MemberDAO.insert(member);
        if (id != -1) {
            member.setId(id);
            memberList.add(member);
            clearFields();
        } else {
            showAlert("Erreur lors de l'ajout du membre.");
        }
    }

    @FXML
    private void handleUpdateMember() {
        Member selected = memberListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un membre à modifier.");
            return;
        }

        selected.setName(nameField.getText().trim());
        selected.setEmail(emailField.getText().trim());
        selected.setType(typeComboBox.getValue());

        if (MemberDAO.update(selected)) {
            memberListView.refresh();
            clearFields();
        } else {
            showAlert("Erreur lors de la mise à jour.");
        }
    }

    @FXML
    private void handleDeleteMember() {
        Member selected = memberListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un membre à supprimer.");
            return;
        }

        if (MemberDAO.delete(selected.getId())) {
            memberList.remove(selected);
            clearFields();
        } else {
            showAlert("Erreur lors de la suppression.");
        }
    }

    @FXML
    private void clearFields() {
        nameField.clear();
        emailField.clear();
        typeComboBox.setValue(null);
        memberListView.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
