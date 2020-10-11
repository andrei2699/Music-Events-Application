package services.implementation;

import services.IStorageManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataBaseSystemManager implements IStorageManager {
    private final String ABSOLUTE_PATH = "http://localhost/MEA-PHP/";

    @Override
    public String readContent(String fileName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ABSOLUTE_PATH + fileName))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.body();
    }

    public String writeContent(String fileName, String content) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ABSOLUTE_PATH + fileName))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.body();
    }

    @Override
    public void initStorageUnit(String fileName, String content) {

    }
}
