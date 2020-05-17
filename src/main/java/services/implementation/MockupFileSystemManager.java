package services.implementation;

import services.AbstractFileSystem;

import java.io.IOException;
import java.nio.file.Path;

public class MockupFileSystemManager extends AbstractFileSystem {
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
