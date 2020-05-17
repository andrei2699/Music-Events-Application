package services.implementation;

import services.FileSystemStoreStrategy;

import java.io.IOException;
import java.nio.file.Path;

public class MockupStoreStrategy implements FileSystemStoreStrategy {
    @Override
    public void createJSONFiles() throws IOException {

    }

    @Override
    public String readFileContent(Path path) {
        return null;
    }

    @Override
    public void writeToFile(Path path, String content) {

    }
}
