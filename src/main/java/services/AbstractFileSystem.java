package services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractFileSystem {
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

    public abstract void createJSONFiles() throws IOException;

    public abstract String readFileContent(Path path);

    public abstract void writeToFile(Path path, String content);

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
