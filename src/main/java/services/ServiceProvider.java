package services;

import services.implementations.ArtistServiceImpl;
import services.implementations.BarServiceImpl;
import services.implementations.UserServiceImpl;
import services.interfaces.ArtistService;
import services.interfaces.BarService;
import services.interfaces.UserService;

public final class ServiceProvider {
    private static final FileSystemManager fileSystemManager = new FileSystemManager();
    private static final UserService userService = new UserServiceImpl();
    private static final BarService barService = new BarServiceImpl();
    private static final ArtistService artistService = new ArtistServiceImpl();

    private ServiceProvider() {
    }

    public static FileSystemManager getFileSystemManager() {
        return fileSystemManager;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static BarService getBarService() {
        return barService;
    }

    public static ArtistService getArtistService() {
        return artistService;
    }
}
