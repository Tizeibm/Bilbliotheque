package com.POO.model;

public class Member {
    private int id;
    private String name;
    private String email;
    private String type; // "Étudiant" ou "Enseignant"

    public Member(String name, String email, String type) {
        this(-1, name, email, type);
    }

    public Member(int id, String name, String email, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getType() { return type; }

    // --- Setters ---
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
