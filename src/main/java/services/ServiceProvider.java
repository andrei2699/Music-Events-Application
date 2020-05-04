package services;

public final class ServiceProvider {
    private static final FileSystemManager fileSystemManager = new FileSystemManager();
    private static final UserService userService = new UserService();
    private static final BarService barService = new BarService();
    private static final ArtistService artistService = new ArtistService();

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
