package services;

import services.implementations.ArtistServiceImpl;
import services.implementations.BarServiceImpl;
import services.implementations.UserServiceImpl;

public final class ServiceProvider {
    private static final FileSystemManager fileSystemManager = new FileSystemManager();
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final BarServiceImpl barService = new BarServiceImpl();
    private static final ArtistServiceImpl artistService = new ArtistServiceImpl();

    private ServiceProvider() {

    }

    public static FileSystemManager getFileSystemManager() {
        return fileSystemManager;
    }

    public static UserServiceImpl getUserService() {
        return userService;
    }

    public static BarServiceImpl getBarService() {
        return barService;
    }

    public static ArtistServiceImpl getArtistService() {
        return artistService;
    }
}
