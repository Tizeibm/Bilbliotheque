package com.POO.db;

import com.POO.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    /* Creer la table livre */

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                genre TEXT,
                height INTEGER,
                publisher TEXT
            );
        """;
        try (Statement stmt = DB.getConnection().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* inserer livres */

    public static int insert(Book book) {
        String sql = "INSERT INTO books (title, author, genre, height, publisher) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setInt(4, book.getHeight());
            pstmt.setString(5, book.getPublisher());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /* affiche tout les livres */

    public static List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement stmt = DB.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("height"),
                        rs.getString("publisher")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /* delete livres */

    public static boolean delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* met a jours livre */

    public static boolean update(Book book) {
        String sql = """
            UPDATE books SET title = ?, author = ?, genre = ?, height = ?, publisher = ?
            WHERE id = ?
        """;
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setInt(4, book.getHeight());
            pstmt.setString(5, book.getPublisher());
            pstmt.setInt(6, book.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
