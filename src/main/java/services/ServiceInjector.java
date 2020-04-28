package services;

public final class ServiceInjector {
    private static ServiceInjector instance;
    private static FileSystemManager fileSystemManager;
    private static UserService userService;

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

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
