package services.implementation;

import services.FileSystemStoreStrategy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemManager {
    public static final String USER_FOLDER = System.getProperty("user.home");

    private static final String APPLICATION_FOLDER = ".MusicEventsApplication";
    protected static final String DATA_FOLDER_NAME = "data";

    protected static final String USERS_JSON_FILE_NAME = "users.json";
    protected static final String BARS_JSON_FILE_NAME = "bars.json"; //localuri
    protected static final String ARTISTS_JSON_FILE_NAME = "artists.json";
    protected static final String EVENTS_JSON_FILE_NAME = "events.json";
    protected static final String RESERVATIONS_JSON_FILE_NAME = "reservations.json";

    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    private FileSystemStoreStrategy storeStrategy;

    public FileSystemManager(FileSystemStoreStrategy storeStrategy) {
        this.storeStrategy = storeStrategy;
    }

    public void setStoreStrategy(FileSystemStoreStrategy storeStrategy) {
        this.storeStrategy = storeStrategy;
    }

    public void createJSONFiles() throws IOException {
        storeStrategy.createJSONFiles();
    }

    public String readFileContent(Path path) {
        return storeStrategy.readFileContent(path);
    }

    public void writeToFile(Path path, String content) {
        storeStrategy.writeToFile(path, content);
    }

    public static Path getPathToFile(String... path) {
        return Paths.get(APPLICATION_HOME_PATH.toFile().getPath(), path);
    }


    public static Path getUsersFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, USERS_JSON_FILE_NAME);
    }

    public static Path getBarsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, BARS_JSON_FILE_NAME);
    }

    public static Path getArtistsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, ARTISTS_JSON_FILE_NAME);
    }

    public static Path getEventsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, EVENTS_JSON_FILE_NAME);
    }

    public static Path getReservationsFilePath() {
        return getPathToFile(DATA_FOLDER_NAME, RESERVATIONS_JSON_FILE_NAME);
    }


}