package services;

public class ServiceInjector {
    private static ServiceInjector instance;
    private static FileSystemManager fileSystemManager;

    private ServiceInjector() {

    }

    public static ServiceInjector getInstance() {
        if (instance == null) {
            instance = new ServiceInjector();
        }
        return instance;
    }

    public FileSystemManager getFileSystemManager() {
        if (fileSystemManager == null) {
            fileSystemManager = new FileSystemManager();
        }
        return fileSystemManager;
    }
}
