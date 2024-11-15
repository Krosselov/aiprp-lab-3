package com.example.dictionary.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheClient {
    public static void main(String[] args) {
        // Тестовое слово для перевода
        String word = "тест";

        // Формирование URL с параметрами
        String url = "http://localhost:8080/MyServlet?txt=" + word + "&direction=ru_to_en&trans=";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Создаем GET-запрос
            HttpGet request = new HttpGet(url);

            // Выполняем запрос
            HttpResponse response = client.execute(request);

            // Получаем результат ответа
            String result = EntityUtils.toString(response.getEntity());

            // Выводим результат в консоль
            System.out.println("Response from server: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

