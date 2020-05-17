package services.implementation;

import org.junit.*;
import services.ServiceProvider;

import java.io.IOException;
import java.nio.file.Files;

public class FileSystemManagerTest {

    private FileSystemManager fileSystemManager;

    @Before
    public void setUp() {
        fileSystemManager = ServiceProvider.getFileSystemManager();
    }

    @After
    public void tearDown() {
        fileSystemManager = null;
    }

    @Test
    public void createJSONFiles() {
        try {
            fileSystemManager.createJSONFiles();

            Assert.assertTrue("Folder data exists", Files.exists(FileSystemManager.getPathToFile("data")));

            Assert.assertTrue("Users JSON file exists", Files.exists(FileSystemManager.getUsersFilePath()));
            Assert.assertTrue("Bars JSON file exists", Files.exists(FileSystemManager.getBarsFilePath()));
            Assert.assertTrue("Artists JSON file exists", Files.exists(FileSystemManager.getArtistsFilePath()));
            Assert.assertTrue("Events JSON file exists", Files.exists(FileSystemManager.getEventsFilePath()));
            Assert.assertTrue("Reservations JSON file exists", Files.exists(FileSystemManager.getReservationsFilePath()));

        } catch (IOException e) {
            Assert.fail("Cannot create folder structure");
        }
    }
}