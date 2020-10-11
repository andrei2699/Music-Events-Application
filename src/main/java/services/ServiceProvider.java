package services;

import models.*;
import repository.implemenation.JSONRepository;
import services.implementation.*;

public final class ServiceProvider {
    private static final IStorageManager storageManager = new DataBaseSystemManager();
    private static final IUserService userService = new UserServiceImpl(new JSONRepository<>(UserModel.class, storageManager));
    private static final IBarService barService = new BarServiceImpl(new JSONRepository<>(BarModel.class, storageManager));
    private static final IArtistService artistService = new ArtistServiceImpl(new JSONRepository<>(ArtistModel.class, storageManager));
    private static final IEventService eventService = new EventServiceImpl(new JSONRepository<>(EventModel.class, storageManager));
    private static final IReservationService reservationService = new ReservationServiceImpl(new JSONRepository<>(ReservationModel.class, storageManager));
    private static final IDiscussionService discussionService = new DiscussionServiceImpl(new JSONRepository<>(DiscussionModel.class, storageManager));
    private static final ICrashService crashService = new CrashServiceImpl(new JSONRepository<>(CrashServiceModel.class, storageManager, false));

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

    public static IDiscussionService getDiscussionService() {
        return discussionService;
    }

    public static ICrashService getCrashService() {
        return crashService;
    }
}
