package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemManager {
    private static final String APPLICATION_FOLDER = ".MusicEventsApplication";
    private static final String USER_FOLDER = System.getProperty("user.home");

    private static final String DATA_FOLDER_NAME = "data";
    private static final String IMAGES_FOLDER_NAME = "images";

    private static final String USERS_JSON_FILE_NAME = "users.json";
    private static final String BARS_JSON_FILE_NAME = "bars.json"; //localuri
    private static final String ARTISTS_JSON_FILE_NAME = "artists.json";

    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static Path getPathToFile(String... path) {
        return Paths.get(APPLICATION_HOME_PATH.toFile().getPath(), path);
    }

    FileSystemManager() {
    }

    public void createJSONFiles() throws IOException {

        Path imagesDirectoryPath = getPathToFile(IMAGES_FOLDER_NAME);
        if (!Files.exists(imagesDirectoryPath)) {
            Files.createDirectories(imagesDirectoryPath);
        }

        Path dataDirectoryPath = getPathToFile(DATA_FOLDER_NAME);
        if (!Files.exists(dataDirectoryPath)) {
            Files.createDirectories(dataDirectoryPath);
        }

        Path usersFilePath = dataDirectoryPath.resolve(USERS_JSON_FILE_NAME);
        if (!Files.exists(usersFilePath)) {
            Files.createFile(usersFilePath);
            writeEmptyJSONArrayToFile(usersFilePath);
        }

        Path barsFilePath = dataDirectoryPath.resolve(BARS_JSON_FILE_NAME);
        if (!Files.exists(barsFilePath)) {
            Files.createFile(barsFilePath);
            writeEmptyJSONArrayToFile(barsFilePath);
        }

        Path artistsFilePath = dataDirectoryPath.resolve(ARTISTS_JSON_FILE_NAME);
        if (!Files.exists(artistsFilePath)) {
            Files.createFile(artistsFilePath);
            writeEmptyJSONArrayToFile(artistsFilePath);
        }
    }

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

    public void writeToFile(Path path, String content) {
        if (Files.exists(path)) {
            try {
                Files.writeString(path, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Path getUsersFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, USERS_JSON_FILE_NAME);
    }

    public Path getBarsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, BARS_JSON_FILE_NAME);
    }

    public Path getArtistsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, ARTISTS_JSON_FILE_NAME);
    }

    private void writeEmptyJSONArrayToFile(Path path) throws IOException {
        if (!Files.exists(path)) return;

        Files.writeString(path, "[]");
    }
}