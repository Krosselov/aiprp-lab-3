package com.example.dictionary.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/audio")
public class AudioServlet extends HttpServlet {

    // Путь к музыкальному файлу
    private static final String AUDIO_FILE_PATH = "D:/Music/track.mp3";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем файл
        File audioFile = new File(AUDIO_FILE_PATH);

        // Проверка, что файл существует
        if (!audioFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Audio file not found");
            return;
        }

        // Устанавливаем заголовки для ответа
        response.setContentType("audio/mp3");  // MIME-тип для mp3
        response.setContentLengthLong(audioFile.length());  // Размер файла

        // Чтение файла и отправка данных клиенту
        try (FileInputStream fileInputStream = new FileInputStream(audioFile);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
    }
}