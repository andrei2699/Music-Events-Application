package services.implementation;

import services.IStorageManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemManager implements IStorageManager {
    public static final String USER_FOLDER = System.getProperty("user.home");

    private static final String APPLICATION_FOLDER = ".MusicEventsApplication";
    protected static final String CRASH_REPORTS_FOLDER_NAME = "crash_reports";

    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static Path getPathToFile(String... path) {
        return Paths.get(APPLICATION_HOME_PATH.toFile().getPath(), path);
    }

    @Override
    public String readContent(String fileName) {
        Path path = getPathToFile(fileName);

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
    public String writeContent(String fileName, String content) {
        Path path = getPathToFile(fileName);

        if (Files.exists(path)) {
            try {
                Files.writeString(path, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public void initStorageUnit(String fileName, String content) {
        Path filePath = getPathToFile(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (NoSuchFileException e) {
                try {
                    Files.createDirectory(filePath.getParent());
                    Files.createFile(filePath);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeContent(fileName, content);
        }
    }
}