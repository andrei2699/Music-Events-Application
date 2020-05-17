package services;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSystemStoreStrategy {
    void createJSONFiles() throws IOException;

    String readFileContent(Path path);

    void writeToFile(Path path, String content);
}
