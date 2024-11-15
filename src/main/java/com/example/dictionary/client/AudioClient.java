package com.example.dictionary.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AudioClient {

    public static void main(String[] args) {
        try {
            // URL сервлета, который отдаёт аудиофайл
            String audioUrl = "http://localhost:8080/audio"; // Укажите URL вашего сервлета
            HttpURLConnection connection = (HttpURLConnection) new URL(audioUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // Получаем поток данных с сервера
            InputStream inputStream = connection.getInputStream();

            // Папка для сохранения скачанного файла
            File downloadDir = new File("downloads");
            if (!downloadDir.exists()) {
                downloadDir.mkdir(); // Создаём папку, если её нет
            }

            // Создаём файл для аудиофайла в папке
            File tempFile = new File(downloadDir, "downloaded-audio.mp3");

            try (OutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            // Открытие MP3 файла с помощью стандартного приложения
            openFile(tempFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для открытия файла с помощью стандартного приложения
    private static void openFile(File file) {
        try {
            // Проверяем, доступен ли Desktop API на текущей платформе
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file); // Открытие файла с помощью ассоциированной программы
                } else {
                    System.out.println("Файл не найден.");
                }
            } else {
                System.out.println("Desktop API не поддерживается на этой платформе.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
