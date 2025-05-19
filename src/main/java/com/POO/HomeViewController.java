package com.POO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class HomeViewController {

    @FXML
    private AnchorPane conteneurPrincipal;

    @FXML
    void chargerDashbord(ActionEvent event) {
        try {
            Parent vue = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/POO/dashboard.fxml")));
            conteneurPrincipal.getChildren().setAll(vue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chargerLivres(ActionEvent event) {
        try {
            Parent vue = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/POO/BookView.fxml")));
            conteneurPrincipal.getChildren().setAll(vue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chargerMembres(ActionEvent event) {
        try {
            Parent vue = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/POO/MemberView.fxml")));
            conteneurPrincipal.getChildren().setAll(vue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chargerEmprunts(ActionEvent event) {
        try {
            Parent vueEmprunts = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/POO/EmpruntView.fxml")));
            conteneurPrincipal.getChildren().setAll(vueEmprunts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
