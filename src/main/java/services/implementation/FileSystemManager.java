package services.implementation;

import services.AbstractFileSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemManager extends AbstractFileSystem {
    public static final String USER_FOLDER = System.getProperty("user.home");

    private static final String APPLICATION_FOLDER = ".MusicEventsApplication";
    private static final String DATA_FOLDER_NAME = "data";

    private static final String USERS_JSON_FILE_NAME = "users.json";
    private static final String BARS_JSON_FILE_NAME = "bars.json"; //localuri
    private static final String ARTISTS_JSON_FILE_NAME = "artists.json";
    private static final String EVENTS_JSON_FILE_NAME = "events.json";
    private static final String RESERVATIONS_JSON_FILE_NAME = "reservations.json";

    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static Path getPathToFile(String... path) {
        return Paths.get(APPLICATION_HOME_PATH.toFile().getPath(), path);
    }

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