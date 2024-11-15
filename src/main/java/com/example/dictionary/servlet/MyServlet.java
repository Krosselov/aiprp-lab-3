package com.example.dictionary.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // Этот метод вызывается при инициализации сервлета
        System.out.println("Сервлет MyServlet инициализирован");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Этот метод вызывается для обработки каждого запроса
        super.service(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String word = request.getParameter("txt");
        String direction = request.getParameter("direction"); // Новый параметр для направления перевода
        String translation;

        if ("ru_to_en".equals(direction)) {
            translation = findTranslation(word, "ru_to_en");
        } else {
            translation = findTranslation(word, "en_to_ru");
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Translation</title></head><body>");
            out.println("<h2>Перевод слова: " + word + " = " + translation + "</h2>");
            out.println("</body></html>");
        }
    }

    private String findTranslation(String word, String direction) {
        String translation = "Not found";
        String jdbcUrl = "jdbc:mysql://localhost:3306/lab3";
        String username = "root";
        String password = "justdb";
        String query;

        // В зависимости от направления запроса, выбираем нужный запрос
        if ("ru_to_en".equals(direction)) {
            query = "SELECT translation_en FROM dictionary WHERE word_ru = ?";
        } else {
            query = "SELECT word_ru FROM dictionary WHERE translation_en = ?";
        }

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, word);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                translation = "ru_to_en".equals(direction) ? rs.getString("translation_en") : rs.getString("word_ru");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translation;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
