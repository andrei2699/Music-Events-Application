package services.implementation;

import services.FileSystemStoreStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static services.implementation.FileSystemManager.*;

public class JSONStoreStrategy implements FileSystemStoreStrategy {
    @Override
    public void createJSONFiles() throws IOException {

        Path dataDirectoryPath = getPathToFile(DATA_FOLDER_NAME);
        if (!Files.exists(dataDirectoryPath)) {
            Files.createDirectories(dataDirectoryPath);
        }

        createJsonFileFromPath(dataDirectoryPath, USERS_JSON_FILE_NAME);
        createJsonFileFromPath(dataDirectoryPath, EVENTS_JSON_FILE_NAME);
        createJsonFileFromPath(dataDirectoryPath, ARTISTS_JSON_FILE_NAME);
        createJsonFileFromPath(dataDirectoryPath, BARS_JSON_FILE_NAME);
        createJsonFileFromPath(dataDirectoryPath, RESERVATIONS_JSON_FILE_NAME);
    }

    @Override
    public String readFileContent(Path path) {
        String result = "";
        if (Files.exists(path)) {
            try {
                result = Files.readString(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void writeToFile(Path path, String content) {
        if (Files.exists(path)) {
            try {
                Files.writeString(path, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeEmptyJSONArrayToFile(Path path) throws IOException {
        if (!Files.exists(path)) return;

        Files.writeString(path, "[]");
    }

    private void createJsonFileFromPath(Path dataDirectoryPath, String path) throws IOException {
        Path filePath = dataDirectoryPath.resolve(path);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            writeEmptyJSONArrayToFile(filePath);
        }
    }
}
