package com.POO.model;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    // Constructeur pour l'ajout d'un emprunt
    public Emprunt(Member member, Book book, LocalDate borrowDate) {
        this(-1, member, book, borrowDate, null);
    }

    // Constructeur pour la lecture depuis la DB (avec ID et date de retour)
    public Emprunt(int id, Member member, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // --- Getters et Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return book.getTitle() + " emprunté par " + member.getName() + " le " + borrowDate + " date de retour : " + returnDate;
    }
}
