package com.POO;

import com.POO.db.BookDAO;
import com.POO.db.DB;
import com.POO.db.EmpruntDAO;
import com.POO.db.MemberDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Connexion et création des tables au démarrage
        DB.connect();
        BookDAO.createTable();
        MemberDAO.createTable();
        EmpruntDAO.createTable();

        scene = new Scene(loadFXML("HomeView"));
        stage.setScene(scene);
        stage.setTitle("Bibliotheque");
        stage.show();
    }

    // Méthode appelée lorsque l'application se ferme
    @Override
    public void stop() {
        System.out.println("Fermeture de l'application...");
        DB.disconnect(); // Fermer la connexion DB
    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String[] args) {
        launch(args);
    }
}