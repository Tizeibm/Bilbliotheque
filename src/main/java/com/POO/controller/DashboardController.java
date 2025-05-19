package com.POO.controller;

import com.POO.db.BookDAO;
import com.POO.db.MemberDAO;
import com.POO.model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class DashboardController {

    @FXML private Label bookCountLabel;
    @FXML private Label memberCountLabel;
    @FXML private Label studentCountLabel;
    @FXML private Label teacherCountLabel;

    @FXML
    public void initialize() {
        int bookCount = BookDAO.getAll().size();
        List<Member> members = MemberDAO.getAll();
        int memberCount = members.size();
        int studentCount = (int) members.stream().filter(m -> m.getType().equalsIgnoreCase("etudiant")).count();
        int teacherCount = (int) members.stream().filter(m -> m.getType().equalsIgnoreCase("enseignant")).count();

        bookCountLabel.setText(String.valueOf(bookCount));
        memberCountLabel.setText(String.valueOf(memberCount));
        studentCountLabel.setText(String.valueOf(studentCount));
        teacherCountLabel.setText(String.valueOf(teacherCount));
    }
}
