package com.POO.model;

public class Book {
    private int id; // Ajout de l'ID
    private String title;
    private String author;
    private String genre;
    private int height;
    private String publisher;

    // Constructeur pour créer un nouvel objet (l'ID sera généré par la DB)
    public Book(String title, String author, String genre, int height, String publisher) {
        this(-1, title, author, genre, height, publisher); // Appelle l'autre constructeur avec un ID temporaire
    }

    // Constructeur utilisé lors de la lecture depuis la DB (avec ID)
    public Book(int id, String title, String author, String genre, int height, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.height = height;
        this.publisher = publisher;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getHeight() {
        return height;
    }

    public String getPublisher() {
        return publisher;
    }

    // --- Setters (utiles pour la modification) ---
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    @Override
    public String toString() {
        // Garder simple pour l'affichage dans la ListView
        return title + " par " + author;
    }

    // Optionnel : equals() et hashCode() basés sur l'ID si vous manipulez des collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book_ = (Book) o;
        return id == book_.id && id != -1; // Comparer par ID si > -1
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}