package com.POO.db;

import com.POO.model.Book;
import com.POO.model.Emprunt;
import com.POO.model.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    public static void createTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS emprunts (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            member_id INTEGER,
            book_id INTEGER,
            borrow_date TEXT,
            return_date TEXT,
            FOREIGN KEY(member_id) REFERENCES members(id),
            FOREIGN KEY(book_id) REFERENCES books(id)
        );
        """;
        try (Statement stmt = DB.getConnection().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insert(Emprunt emprunt) {
        String sql = "INSERT INTO emprunts (member_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, emprunt.getMember().getId());
            pstmt.setInt(2, emprunt.getBook().getId());
            pstmt.setString(3, emprunt.getBorrowDate().toString());
            pstmt.setString(4, (emprunt.getReturnDate() != null) ? emprunt.getReturnDate().toString() : null);
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Emprunt> getAll() {
        List<Emprunt> list = new ArrayList<>();
        String sql = """
        SELECT 
            e.id AS emprunt_id, 
            e.borrow_date, 
            e.return_date,
            m.id AS member_id, m.name AS member_name, m.type AS member_type, m.email AS member_email,
            b.id AS book_id, b.title AS book_title, b.author, b.genre, b.height, b.publisher
        FROM emprunts e
        JOIN members m ON e.member_id = m.id
        JOIN books b ON e.book_id = b.id
        """;

        try (Statement stmt = DB.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("member_id"),
                        rs.getString("member_name"),
                        rs.getString("member_type"),
                        rs.getString("member_email")
                );

                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("book_title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("height"),
                        rs.getString("publisher")
                );

                Emprunt emprunt = new Emprunt(
                        rs.getInt("emprunt_id"),
                        member,
                        book,
                        LocalDate.parse(rs.getString("borrow_date")),
                        rs.getString("return_date") != null ? LocalDate.parse(rs.getString("return_date")) : null
                );

                list.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static boolean updateReturnDate(int empruntId, LocalDate returnDate) {
        String sql = "UPDATE emprunts SET return_date = ? WHERE id = ?";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, returnDate.toString());
            pstmt.setInt(2, empruntId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
