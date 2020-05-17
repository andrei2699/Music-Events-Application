package services;

import repository.implemenation.*;
import services.implementation.*;

public final class ServiceProvider {
    private static final IStorageManager storageManager = new FileSystemManager();
    private static final IUserService userService = new UserServiceImpl(new UserRepository(storageManager));
    private static final IBarService barService = new BarServiceImpl(new BarRepository(storageManager));
    private static final IArtistService artistService = new ArtistServiceImpl(new ArtistRepository(storageManager));
    private static final IEventService eventService = new EventServiceImpl(new EventRepository(storageManager));
    private static final IReservationService reservationService = new ReservationServiceImpl(new ReservationRepository(storageManager));

    private ServiceProvider() {
    }

    public static IUserService getUserService() {
        return userService;
    }

    public static IBarService getBarService() {
        return barService;
    }

    public static IArtistService getArtistService() {
        return artistService;
    }

    public static IEventService getEventService() {
        return eventService;
    }

    public static IReservationService getReservationService() {
        return reservationService;
    }
}
